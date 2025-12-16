/**
 * A BlockRemover is in charge of removing blocks from the game,
 * as well as keeping count of the remaining blocks.
 */
public class BlockRemover implements HitListener {

    private Game game;
    private Counter remainingBlocks;

    /**
     * Construct a BlockRemover.
     *
     * @param game the game from which blocks will be removed
     * @param remainingBlocks counter for remaining blocks
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    /**
     * Blocks that are hit should be removed from the game.
     * Remember to remove this listener from the block that is being removed.
     *
     * @param beingHit the block being hit
     * @param hitter the ball that hit the block (unused for removal)
     */
    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeHitListener(this);
        beingHit.removeFromGame(this.game);
        this.remainingBlocks.decrease(1);
    }
}
