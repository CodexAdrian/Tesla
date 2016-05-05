package net.darkhax.tesla.lib;

import java.util.ArrayList;
import java.util.List;

import net.darkhax.tesla.api.ITeslaHandler;
import net.darkhax.tesla.capability.TeslaStorage;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class TeslaUtils {
    
    /**
     * The smallest unit of power measurement.
     */
    public static final int TESLA = 1;
    
    /**
     * The amount of Tesla in a KiloTesla. One thousand.
     */
    public static final int KILOTESLA = 1000;
    
    /**
     * The amount of Telsa in a MegaTesla. One Million.
     */
    public static final int MEGATESLA = 1000000;
    
    /**
     * The amount of Tesla in a GigaTesla. One Billion.
     */
    public static final int GIGATESLA = 1000000000;
    
    /**
     * The amount of Telsa in a TeraTesla. One Trillion.
     */
    public static final long TERATESLA = 1000000000000L;
    
    /**
     * The amount of Tesla in a PentaTesla. One Quadrilli.on
     */
    public static final long PENTATESLA = 1000000000000000L;
    
    /**
     * The amount of Tesla in a ExaTesla. One Quintilian.
     * 
     * The ExaTesla should not be treated as the largest unit of Tesla. The naming scheme can
     * go on indefinitely. The next unit would be a ZettaTesla, followed by YottaTesla,
     * BronoTesla, GeopTesla and so on. While it is possible to define these measurements,
     * there really isn't a point.
     */
    public static final long EXATESLA = 1000000000000000000L;
    
    /**
     * Converts an amount of Tesla units into a human readable String. The string amount is
     * only rounded to one decimal place.
     * 
     * @param tesla The amount of Tesla units to display.
     * @return A human readable display of the Tesla units.
     */
    public static String getDisplayableTeslaCount (long tesla) {
        
        if (tesla < 1000)
            return tesla + " T";
            
        final int exp = (int) (Math.log(tesla) / Math.log(1000));
        final char unitType = "KMGTPE".charAt(exp - 1);
        return String.format("%.1f %sT", tesla / Math.pow(1000, exp), unitType);
    }
    
    /**
     * Gets the abbreviated name of the best unit to describe the provided power. For example,
     * anything less than 1000 will return t for tesla, while anything between 999 and one
     * million will return kt for kilo tesla. This method has support for up to Exatesla.
     * 
     * @param tesla The amount of Tesla to get the unit for.
     * @return The abbreviated name for the unit used to describe the provided power amount.
     */
    public static String getUnitType (long tesla) {
        
        if (tesla < 1000)
            return tesla + "t";
            
        final int exp = (int) (Math.log(tesla) / Math.log(1000));
        return "kmgtpe".charAt(exp - 1) + "t";
    }
    
    /**
     * Gets the name of the the power unit that best represents the amount of provided power.
     * The name will be translated to the local language, or english if that language is not
     * yet supported.
     * 
     * @param tesla The amount of Tesla to get the unit name for.
     * @return The name of the power unit that best represents the amount of power provided.
     */
    public static String getLocalizedUnitType (long tesla) {
        
        return I18n.translateToLocal("unit.tesla." + getUnitType(tesla));
    }
    
    /**
     * Gets a List of all the ITeslaHandlers for the blocks that are touching the passed
     * position. For a valid ITeslaHandler to be detected, it must be attached to a valid
     * TileEntity that is directly touching the passed position.
     * 
     * @param world The world to run the check in.
     * @param pos The position to search around.
     * @return A List of all valid ITeslaHandlers that are around the passed position.
     */
    public static List<ITeslaHandler> getConnectedTeslaHandlers (World world, BlockPos pos) {
        
        final List<ITeslaHandler> teslaHandlers = new ArrayList<ITeslaHandler>();
        
        for (final EnumFacing facing : EnumFacing.values()) {
            
            final TileEntity tile = world.getTileEntity(pos.offset(facing));
            
            if (tile != null && !tile.isInvalid() && tile.hasCapability(TeslaStorage.TESLA_HANDLER_CAPABILITY, facing))
                teslaHandlers.add(tile.getCapability(TeslaStorage.TESLA_HANDLER_CAPABILITY, facing));
        }
        
        return teslaHandlers;
    }
    
    /**
     * Attempts to distribute power to all blocks that are directly touching the passed
     * position. This will check to make sure that each tile is a valid tasla handler and that
     * the direction is a valid input side.
     * 
     * @param world The world to distribute power within.
     * @param pos The position to distribute power around.
     * @param amount The amount of power to distribute to each individual tile.
     * @param simulated Whether or not this is being called as part of a simulation.
     * @return The amount of power that was accepted by the handlers.
     */
    public static long distributePowerEqually (World world, BlockPos pos, long amount, boolean simulated) {
        
        long consumedPower = 0L;
        
        for (final EnumFacing facing : EnumFacing.values()) {
            
            final TileEntity tile = world.getTileEntity(pos.offset(facing));
            
            if (tile != null && !tile.isInvalid() && tile.hasCapability(TeslaStorage.TESLA_HANDLER_CAPABILITY, facing)) {
                
                final ITeslaHandler teslaHandler = tile.getCapability(TeslaStorage.TESLA_HANDLER_CAPABILITY, facing);
                
                if (teslaHandler.isInputSide(facing))
                    consumedPower += teslaHandler.givePower(amount, facing, simulated);
            }
        }
        
        return consumedPower;
    }
    
    /**
     * Attempts to consume power from all blocks that are directly touching the passed
     * position.
     * 
     * @param world The world to take power within.
     * @param pos The position to take power around.
     * @param amount The amount of power to take from each tile.
     * @param simulated Whether or not this is being called as part of a simulation.
     * @return The amount of power that was taken from the handlers.
     */
    public static long consumePowerEqually (World world, BlockPos pos, long amount, boolean simulated) {
        
        long consumedPower = 0L;
        
        for (final EnumFacing facing : EnumFacing.values()) {
            
            final TileEntity tile = world.getTileEntity(pos.offset(facing));
            
            if (tile != null && !tile.isInvalid() && tile.hasCapability(TeslaStorage.TESLA_HANDLER_CAPABILITY, facing)) {
                
                final ITeslaHandler teslaHandler = tile.getCapability(TeslaStorage.TESLA_HANDLER_CAPABILITY, facing);
                
                if (teslaHandler.isOutputSide(facing))
                    consumedPower += teslaHandler.takePower(amount, facing, simulated);
            }
        }
        
        return consumedPower;
    }
}
