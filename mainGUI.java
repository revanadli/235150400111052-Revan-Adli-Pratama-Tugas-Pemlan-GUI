package prakPemlan;
import javax.swing.*;

public class mainGUI {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Form().setVisible(true);
            }
        });
    }
}
