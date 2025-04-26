package archives.tater.defilebestby.mixin;

import doctor4t.defile.block.FuneralInkBlock;
import doctor4t.defile.cca.DefileComponents;
import net.minecraft.block.BlockState;
import net.minecraft.block.MultifaceGrowthBlock;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Arrays;

@SuppressWarnings("deprecation")
@Mixin(FuneralInkBlock.class)
public abstract class FuneralInkBlockMixin extends MultifaceGrowthBlock {
	public FuneralInkBlockMixin(Settings settings) {
		super(settings);
	}

	@Override
	public boolean hasRandomTicks(BlockState state) {
		return true;
	}

	@Override
	public void randomTick(BlockState state, ServerWorld world, BlockPos pos, Random random) {
		super.randomTick(state, world, pos, random);
		if (DefileComponents.BLACK_RAIN.get(world).isRaining()) return;
		if (world.hasRain(pos) || Arrays.stream(DIRECTIONS).noneMatch(direction -> world.getBlockState(pos.offset(direction)).isOf(this)) || random.nextFloat() < 0.1) {
			world.breakBlock(pos, false);
		}
	}
}
