import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;

public class FansyButton extends JLabel implements MouseListener {

    ActionListener myAL;

    public FansyButton(String str){
        super(str);
        Font customFont;
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, this.getClass().getResourceAsStream("/fonts/Minecrafter.Reg.ttf")).deriveFont(50f);
            this.setFont(customFont);
        } catch (FontFormatException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.setForeground(new Color(120,203,0));
        this.setOpaque(false);
        this.addMouseListener(this);
    }

    public void addActionListener(ActionListener al){
        myAL = al;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        myAL.actionPerformed(new ActionEvent(this,501,""));
    }

    @Override
    public void mousePressed(MouseEvent e) {}

    @Override
    public void mouseReleased(MouseEvent e) {}

    @Override
    public void mouseEntered(MouseEvent e) {
        this.setForeground(new Color(243, 105, 66));
    }

    @Override
    public void mouseExited(MouseEvent e) {
        this.setForeground(new Color(120,203,0));
    }
}