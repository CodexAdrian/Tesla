package net.darkhax.tesla.lib;

import net.darkhax.tesla.Tesla;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;


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
     * The amount of Tesla in a PentaTesla. One Quadrillion.
     */
    public static final long PETATESLA = 1000000000000000L;

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
     * Converts an amount of Tesla units into a human-readable String. The string amount is
     * only rounded to one decimal place.
     *
     * @param tesla The amount of Tesla units to display.
     * @return A human-readable display of the Tesla units.
     */
    public static Component getDisplayableTeslaCount (long tesla) {
        return Component.translatable("unit.tesla." + getUnitType(tesla), tesla);
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

        if (tesla < 1000) {
            return "t";
        }

        final int exp = (int) (Math.log(tesla) / Math.log(1000));
        return "kmgtpe".charAt(exp - 1) + "t";
    }

    /**
     * Gets if a block can receive or provide Tesla power.
     * @param level The world the block is in.
     * @param pos The position of the block.
     * @param side The side of the block.
     * @return whether the block can receive or provide Tesla power.
     */
    public static boolean hasTeslaSupport (Level level, BlockPos pos, Direction side) {
        return Tesla.Consumers.BLOCK.find(level, pos, side) != null || Tesla.Producers.BLOCK.find(level, pos, side) != null;
    }

    /**
     * Gets if an item can receive or provide Tesla power.
     * @param stack The item stack.
     * @return whether the item can receive or provide Tesla power.
     */
    public static boolean hasTeslaSupport (ItemStack stack) {
        return Tesla.Consumers.ITEM.find(stack, null) != null || Tesla.Producers.ITEM.find(stack, null) != null;
    }

    public static int getPowerLevel (Level level, BlockPos pos) {
        var power = 0;
        for (Direction direction : Direction.values()) {
            var producer = Tesla.Producers.BLOCK.find(level, pos.relative(direction), direction.getOpposite());
            if (producer != null) {
                power += producer.getProducing();
            }
        }
        return power;
    }
}