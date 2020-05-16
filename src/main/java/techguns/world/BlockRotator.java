package techguns.world;

import net.minecraft.block.*;
import net.minecraft.block.LogBlock;
import net.minecraft.block.LogBlock.EnumAxis;
import net.minecraft.block.TorchBlock;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.BlockState;
import net.minecraft.util.Direction;
import techguns.blocks.BlockTGDoor3x3;

public class BlockRotator {

	public static BlockState getRotatedHorizontal(BlockState state, int times) {
		
		if(state.getProperties().containsKey(HorizontalBlock.FACING)) {
			return getRotatedWithProperty(state, times, HorizontalBlock.FACING);
			
		} else if (state.getProperties().containsKey(DirectionalBlock.FACING)) {
			return getRotatedWithProperty(state, times, DirectionalBlock.FACING);
			
		} else if (state.getProperties().containsKey(TorchBlock.FACING)) {
			return getRotatedWithProperty(state, times, TorchBlock.FACING);
			
		} else if (state.getProperties().containsKey(LeverBlock.FACING)) {
			return getRotatedLever(state, times);
			
		} else if (state.getProperties().containsKey(BlockTGDoor3x3.ZPLANE)) {
			return rotateBooleanPlane(state, times, BlockTGDoor3x3.ZPLANE);
		} else if (state.getProperties().containsKey(LogBlock.LOG_AXIS)) {
			if (times == 1 || times == 3) {
				if (state.getValue(LogBlock.LOG_AXIS) == EnumAxis.X) return state.withProperty(LogBlock.LOG_AXIS, EnumAxis.Z);
				if (state.getValue(LogBlock.LOG_AXIS) == EnumAxis.Z) return state.withProperty(LogBlock.LOG_AXIS, EnumAxis.X);
			}
		}
		
		return state;
	}
	
	public static BlockState getWithFacing(BlockState state, Direction target) {
		if(state.getProperties().containsKey(HorizontalBlock.FACING)) {
			return state.withProperty(HorizontalBlock.FACING, target);
			
		} else if (state.getProperties().containsKey(DirectionalBlock.FACING)) {
			return state.withProperty(DirectionalBlock.FACING, target);
			
		} else if (state.getProperties().containsKey(TorchBlock.FACING)) {
			return state.withProperty(TorchBlock.FACING, target);
		} 
		
		return state;
	}
	
	protected static BlockState getRotatedWithProperty(BlockState state, int times, PropertyDirection property) {
		Direction facing = state.getValue(property);
		if (!(facing == Direction.UP || facing == Direction.DOWN)) {
			while (times-- > 0) {
				facing = facing.rotateYCCW();
			}
		}
		return state.withProperty(property, facing);	
	}
	
	protected static BlockState rotateBooleanPlane(BlockState state, int times, PropertyBool property) {
		boolean plane = state.getValue(property);
		if((times % 2)==0) {
			return state;
		} else {
			return state.withProperty(property, !plane);
		}
	}
	
	protected static BlockState getRotatedLever(BlockState state, int times) {
		LeverBlock.EnumOrientation rot = state.getValue(LeverBlock.FACING);
		while (times-- > 0) {
			switch(rot) {
			case DOWN_X:
				rot = LeverBlock.EnumOrientation.DOWN_Z;
				break;
			case DOWN_Z:
				rot = LeverBlock.EnumOrientation.DOWN_X;
				break;
			case EAST:
				rot = LeverBlock.EnumOrientation.NORTH;
				break;
			case NORTH:
				rot = LeverBlock.EnumOrientation.WEST;
				break;
			case SOUTH:
				rot = LeverBlock.EnumOrientation.EAST;
				break;
			case UP_X:
				rot = LeverBlock.EnumOrientation.UP_Z;
				break;
			case UP_Z:
				rot = LeverBlock.EnumOrientation.UP_X;
				break;
			case WEST:
				rot = LeverBlock.EnumOrientation.SOUTH;
				break;
			default:
				break;		
			}
		}
		return state.withProperty(LeverBlock.FACING, rot);
	}
}
