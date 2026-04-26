package com.fishy.fishyBusiness.block.custom;

import com.fishy.fishyBusiness.block.custom.entity.MarkerBlockEntity;
import com.fishy.fishyBusiness.block.custom.entity.ModBlockEntities;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.AttachFace;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

public class MarkerBlock extends FaceAttachedHorizontalDirectionalBlock implements EntityBlock{
    public static final VoxelShape SHAPE = Block.box(0, 0, 0, 16, 0.0001, 16);

    protected static final VoxelShape FLOOR_SHAPE = Block.box(0, 0, 0, 16, 0.0001, 16);
    protected static final VoxelShape CEILING_SHAPE = Block.box(0, 15.9999, 0, 16, 16, 16);
    protected static final VoxelShape WALL_SHAPE_NORTH = Block.box(0, 0, 15.9999, 16, 16, 16);
    protected static final VoxelShape WALL_SHAPE_EAST = Block.box(0, 0, 0, 0.0001, 16, 16);
    protected static final VoxelShape WALL_SHAPE_SOUTH = Block.box(0, 0, 0, 16, 16, 0.0001);
    protected static final VoxelShape WALL_SHAPE_WEST = Block.box(15.9999, 0, 0, 16, 16, 16);

public static final MapCodec<MarkerBlock> CODEC = simpleCodec(MarkerBlock::new);

    public MarkerBlock(Properties properties) {
        super(properties);
        this.registerDefaultState(
                this.stateDefinition.any().setValue(FACING, Direction.NORTH).setValue(FACE, AttachFace.WALL)
        );

    }

    @Override
    protected VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        Direction direction = state.getValue(FACING);
        switch ((AttachFace) state.getValue(FACE)) {
            case FLOOR:
                    return FLOOR_SHAPE;

            case WALL:
                return switch (direction) {
                    case EAST -> WALL_SHAPE_EAST;
                    case WEST -> WALL_SHAPE_WEST;
                    case SOUTH -> WALL_SHAPE_SOUTH;
                    case NORTH, UP, DOWN -> WALL_SHAPE_NORTH;
                };
            case CEILING:
                return  CEILING_SHAPE;
        }
        return FLOOR_SHAPE;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING, FACE);
    }

    @Override
    protected MapCodec<? extends FaceAttachedHorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Override
    protected void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        MarkerBlockEntity markerBlockEntity = (MarkerBlockEntity) level.getBlockEntity(pos);
        assert markerBlockEntity != null;
        int ranTick = markerBlockEntity.getDecay();
        if(!markerBlockEntity.checkColor()){
            System.out.println(ranTick);
            if(ranTick >= 5){
                this.asBlock().destroy(level, pos, state);
                //level.setBlock(pos, Blocks.RED_CONCRETE.defaultBlockState(),3); debug
            }else{
                markerBlockEntity.addDecay(1);
            }
        }else{
            markerBlockEntity.setDecay(0);
        }

        super.randomTick(state, level, pos, random);
    }

    //Block entity junk

    @Override
    protected RenderShape getRenderShape(BlockState state) {
        return RenderShape.INVISIBLE;
    }

    @Override
    public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return ModBlockEntities.MARKER_BE.get().create(pos, state);
    }

    @Override
    protected ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hitResult) {
        return super.useItemOn(stack, state, level, pos, player, hand, hitResult);
    }
}
