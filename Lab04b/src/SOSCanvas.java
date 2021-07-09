import java.awt.*;
import javax.swing.*;
import cs101.sosgame.SOS;
public class SOSCanvas extends JPanel{

    SOS sosGame;

    public SOSCanvas( SOS sosGame ){
        this.sosGame = sosGame;
        setPreferredSize( new Dimension ( 500, 500 ) );
    }

    @Override
    public void paintComponent( Graphics g ) {
        super.paintComponent( g );

        final int BOX_LENGTHS = 70;

        int gridSize = sosGame.getDimension();
        String[] cellLetters = new String[ (int) Math.pow( gridSize, 2) ];
        int x = 0,
            y = 0;
        char cellContents;

        for ( int i = 0; i < gridSize; i++ ){
            for ( int j = 0; j < gridSize; j++ ){
                cellContents =  sosGame.getCellContents( i, j );
                cellLetters[ x ] = String.valueOf( cellContents );
                x++;
            }
        }

        for ( int i = BOX_LENGTHS; i < BOX_LENGTHS * gridSize; i = i + BOX_LENGTHS){
            for ( int j = BOX_LENGTHS; j < BOX_LENGTHS * gridSize; j = j + BOX_LENGTHS){
                g.drawRect( i, j, BOX_LENGTHS, BOX_LENGTHS);
                g.drawString( cellLetters[ y ], i + ( BOX_LENGTHS / 2 ), j + ( BOX_LENGTHS / 2 ) );
                y++;
            }
        }

    }
}
