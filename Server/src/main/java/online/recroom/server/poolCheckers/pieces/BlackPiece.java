package online.recroom.server.poolCheckers.pieces;

import online.recroom.server.poolCheckers.board.Cell;
import online.recroom.server.poolCheckers.board.CoOrdinates;
import online.recroom.server.poolCheckers.board.CoOrdinatesOutOfBoundsException;


/**
 * Created by theje on 5/7/2016.
 */
public class BlackPiece extends Piece implements ICrownable {
    public BlackPiece() {
        super(Color.BLACK);
    }

    @Override
    protected boolean isRegularMove(Cell destination) {
        return super.isRegularMove(destination) && (destination.getRow() == (this.getRow() - 1));
    }

    @Override
    public boolean isCaptureMove(Cell destination) {
        if (destination.isNotOccupied() && destination.getRow() == (this.getRow() - 2)) {
            
            if (destination.getColumn() == (this.getColumn() + 2)) {

                return cellInBetweenIsCapturable(getRow() - 1, getColumn() + 1);

            } else if (destination.getColumn() == (this.getColumn() - 2)) {

                return cellInBetweenIsCapturable(getRow() - 1, getColumn() - 1);
            }
        }
        return false;
    }

    @Override
    public King crownMe() {
        return new King(this);
    }
}
