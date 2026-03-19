package com.fishy.fishyBusiness.block.custom;

import com.fishy.fishyBusiness.block.ModBlocks;
import com.mojang.serialization.MapCodec;
import joptsimple.internal.Strings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.HolderSet;
import net.minecraft.core.Vec3i;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.vehicle.Boat;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.material.WaterFluid;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.function.Predicate;

public class BlueweedBlock extends BushBlock implements BonemealableBlock {
    public static final MapCodec<BlueweedBlock> CODEC = simpleCodec(BlueweedBlock::new);
    protected static final VoxelShape AABB = Block.box((double)1.0F, (double)0.0F, (double)1.0F, (double)15.0F, (double)1.5F, (double)15.0F);
    public static final BooleanProperty EMISSIVE = BooleanProperty.create("emissive");
    public static final DirectionProperty FACING = HorizontalDirectionalBlock.FACING;

    public BlueweedBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(this.stateDefinition.any()
                .setValue(EMISSIVE, true)); //im setting this as always true for now cause it has a better looking model
    }


    protected void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        super.entityInside(state, level, pos, entity);
        if (level instanceof ServerLevel && entity instanceof Boat) {
            level.destroyBlock(new BlockPos(pos), true, entity);
        }

    }


    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return AABB;
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter level, BlockPos pos) {
        FluidState fluidstate = level.getFluidState(pos);
        FluidState fluidstate1 = level.getFluidState(pos.above());
        return (fluidstate.getType() == Fluids.WATER || state.getBlock() instanceof IceBlock) && fluidstate1.getType() == Fluids.EMPTY;
    }


    @Override
    protected MapCodec<? extends BushBlock> codec() {
        return CODEC;
    }

    @Override
    public @Nullable BlockState getStateForPlacement(BlockPlaceContext context) {
        return this.defaultBlockState().setValue(EMISSIVE, true).setValue(FACING, context.getHorizontalDirection().getOpposite());// same here its always true

    }

//    @Override
//    public void neighborChanged(BlockState state, Level level, BlockPos pos, Block block, BlockPos fromPos, boolean isMoving) {
//        if (!level.isClientSide) {
//            boolean shouldGlow = level.getMaxLocalRawBrightness(pos) <= 8;
//
//            if (state.getValue(EMISSIVE) != shouldGlow) {
//                level.setBlock(pos, state.setValue(EMISSIVE, shouldGlow), 3);
//            }
//        }
//    }



    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, EMISSIVE);
    }

    protected boolean place(LevelAccessor level, BlockPos pos, Direction direction) {
        BlockState blockstate = this.defaultBlockState().setValue(EMISSIVE,true).setValue(FACING, direction);
        return level.setBlock(pos, blockstate, 3);
    }
    @Override
    public boolean isValidBonemealTarget(LevelReader level, BlockPos pos, @NotNull BlockState state) {
//        return level.getBlockState(pos.north()).isAir() && level.getBlockState(pos.below()).getFluidState().getType() == Fluids.WATER
//                || level.getBlockState(pos.east()).isAir() && level.getBlockState(pos.below()).getFluidState().getType() == Fluids.WATER
//                || level.getBlockState(pos.south()).isAir() && level.getBlockState(pos.below()).getFluidState().getType() == Fluids.WATER
//                || level.getBlockState(pos.west()).isAir() && level.getBlockState(pos.below()).getFluidState().getType() == Fluids.WATER;
        return true;
    }

    @Override
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    public boolean isValid(ServerLevel level, BlockPos pos, Direction direction) {
        if (level.getBlockState(pos.relative(direction)).isAir() && level.getFluidState(pos.relative(direction).below()).getType() == Fluids.WATER){
            return true;
        }
    return false;
    }


    @Override
    public void performBonemeal(@NotNull ServerLevel level, RandomSource random, @NotNull BlockPos pos, @NotNull BlockState state) {
        List<Direction> validDirections = new java.util.ArrayList<>(List.of());
        BlockPos posToPlace = null;

        if(isValid( level, pos, Direction.NORTH)){
            validDirections.add(Direction.NORTH);
        }
        if (isValid( level, pos, Direction.EAST)){
            validDirections.add(Direction.EAST);
        }
        if (isValid( level, pos, Direction.SOUTH)){
            validDirections.add(Direction.SOUTH);
        }
        if (isValid( level, pos, Direction.WEST)){
            validDirections.add(Direction.WEST);
        }

        if (validDirections.isEmpty()){
            popResource(level, pos, new ItemStack(this));
        }else {
            posToPlace = new BlockPos(pos.relative((Direction) validDirections.get(random.nextIntBetweenInclusive(0, validDirections.size() - 1))));
            this.place(level, posToPlace, Direction.Plane.HORIZONTAL.getRandomDirection(random));
        }

    }

    @Override
    public Type getType() {
        return Type.GROWER;
    }
}
