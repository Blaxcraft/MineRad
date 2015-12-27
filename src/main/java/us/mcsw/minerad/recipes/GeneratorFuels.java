package us.mcsw.minerad.recipes;

import java.util.ArrayList;

import net.minecraft.item.Item;
import us.mcsw.minerad.init.ModItems;

public class GeneratorFuels {

	private static ArrayList<GeneratorFuel> fuels = new ArrayList<GeneratorFuel>();

	static {
		addFuel(ModItems.uraniumChunk, 100, 20);
		addFuel(ModItems.uraniumOreItem, 800, 25);
		addFuel(ModItems.purifiedUranium, 1200, 30);
		addFuel(ModItems.nuggetPlutonium, 3000, 40);
		addFuel(ModItems.plutonium, 27000, 40);
	}

	public static void addFuel(Item i, int generationTicks, int generationAmount) {
		fuels.add(new GeneratorFuel(i, generationTicks, generationAmount));
	}
	
	public static boolean isFuel(Item i) {
		return getFuelInfoFor(i) != null;
	}
	
	public static GeneratorFuel getFuelInfoFor(Item i) {
		for (GeneratorFuel fuel : fuels) {
			if (fuel.getItem().equals(i)) {
				return fuel;
			}
		}
		return null;
	}

	public static class GeneratorFuel {
		Item i;
		int genTicks, genAmount;

		public GeneratorFuel(Item i, int genTicks, int genAmount) {
			this.i = i;
			this.genTicks = genTicks;
			this.genAmount = genAmount;
		}

		public Item getItem() {
			return i;
		}

		public int getGenAmount() {
			return genAmount;
		}

		public int getGenTicks() {
			return genTicks;
		}
	}

}
