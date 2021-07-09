import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import cs101.sosgame.SOS;

public class SOSGUIPanel extends JPanel{

    final int BOX_LENGTHS = 70;

    JPanel canvas;
    JLabel p1, p2;
    JRadioButton O, S;
    ButtonGroup butGroup;

    int p1Score, p2Score, playerTurn;

    String p1Name, p2Name, p1ScoreString, p2ScoreString;

    char choice;

    SOS sosGame;

    NewMouseListener mouseListener;
    NewRadioListener radioListener;

    public SOSGUIPanel( SOS sosGame, String p1Name, String p2Name ){

        //Initializing
        this.sosGame = sosGame;
        this.p1Name = p1Name;
        this.p2Name = p2Name;
        this.choice = 'S';
        this.p1Score = 0;
        this.p2Score = 0;
        this.playerTurn = sosGame.getTurn();
        this.p1Score = 0;

        this.setLayout( new BorderLayout() );

        canvas = new SOSCanvas( sosGame );
        this.add( canvas, BorderLayout.CENTER );

        mouseListener = new NewMouseListener();
        canvas.addMouseListener( mouseListener );

        canvas.setBackground( Color.PINK );
        canvas.setLayout( new BorderLayout() );

        JPanel panel = new JPanel();
        //Player 1
        p1ScoreString = "" + p1Score;
        p1 = new JLabel(p1Name + ": " + p1ScoreString );
        p1.setOpaque( true );
        if ( playerTurn == 1 ){
            p1.setBackground( Color.CYAN.darker() );
        }
        else {
            p1.setBackground( Color.GRAY );
        }
        panel.add( this.p1 );


        //Player 2
        p2ScoreString = "" + p2Score;
        p2 = new JLabel(p2Name + ": " + p2ScoreString );
        p2.setOpaque( true );
        if ( playerTurn == 2 ){
            p2.setBackground( Color.CYAN.darker() );
        }
        else {
            p2.setBackground( Color.GRAY );
        }
        panel.add( this.p2 );
        this.add( panel, BorderLayout.NORTH );

        //Radio button setting
        S = new JRadioButton( "S", true);
        O = new JRadioButton( "O");
        butGroup = new ButtonGroup();
        butGroup.add( S );
        butGroup.add( O );
        JPanel butPanel = new JPanel();
        butPanel.add( S );
        butPanel.add( O );
        this.add( butPanel, BorderLayout.SOUTH);
        radioListener = new NewRadioListener();
        S.addActionListener( radioListener );
        O.addActionListener( radioListener );

        //Panel size
        setPreferredSize( new Dimension( 700, 700));
    }

    class NewMouseListener extends MouseAdapter {
        public void mousePressed( MouseEvent  mouseEvent ){
            Point point = mouseEvent.getPoint();

            int column = (int) ((point.getX()) / (BOX_LENGTHS) + 1);
            int row = (int) ((point.getY()) / (BOX_LENGTHS) + 1);
            sosGame.play( choice, row, column);

            //Score updates
            p1Score = sosGame.getPlayerScore1();
            p1ScoreString = "" + p1Score;
            p1.setText( p1Name + ": " + p1ScoreString);
            p2Score = sosGame.getPlayerScore2();
            p2ScoreString = "" + p2Score;
            p2.setText( p2Name + ": " + p2ScoreString);

            playerTurn = sosGame.getTurn();
            if ( playerTurn == 1 ) {
                p1.setBackground( Color.CYAN.darker());
                p2.setBackground( Color.GRAY);
            }
            else {
                p2.setBackground( Color.CYAN.darker());
                p1.setBackground( Color.GRAY);
            }

            repaint();

            if ( sosGame.isGameOver() )
            {
                if ( p1Score > p2Score ) {
                    JOptionPane.showMessageDialog( canvas, p1Name + " is the winner!");
                }
                else if ( p1Score < p2Score ) {
                    JOptionPane.showMessageDialog( canvas, p2Name + " is the winner!");
                }
                else {
                    JOptionPane.showMessageDialog( canvas, "It's a draw!");
                }
            }
        }
    }

        class NewRadioListener implements ActionListener {
        public void actionPerformed( ActionEvent e ) {
            Object object = e.getSource();
            if ( object == S ) {
                choice = 'S';
            }
            else {
                choice = 'O';
            }
        }
    }

    public static void main( String[] args) {

        JFrame   frame = new JFrame( "SOS GAME" );
        JLabel   label = new JLabel( "SOS GAME" );

        SOS sosGame = new SOS( 5 );

        frame = new JFrame();
        frame.getContentPane().add( new SOSGUIPanel( sosGame, "p1", "p2") );
        frame.setLayout( new FlowLayout( FlowLayout.CENTER ) );
        frame.pack();
        frame.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
        frame.setVisible( true);
    }
}
