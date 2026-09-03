package smartcampus;
import java.awt.*; import java.awt.event.*; import java.sql.*;
public class Main extends Frame {
    TextField user=new TextField(), pass=new TextField(); Label msg=new Label(" ");
    public Main(){ super("Smart Campus Parking & Traffic Intelligence"); setSize(760,470); setLayout(new BorderLayout());
        Panel top=new Panel(new GridLayout(1,1)); top.setBackground(UI.NAVY); Label t=UI.label("SMART CAMPUS PARKING INTELLIGENCE",28,Color.WHITE); top.add(t); add(top,BorderLayout.NORTH);
        Panel center=new Panel(new GridBagLayout()); center.setBackground(UI.BG); GridBagConstraints g=new GridBagConstraints(); g.insets=new Insets(10,10,10,10); g.fill=GridBagConstraints.HORIZONTAL;
        Panel card=UI.card(430,250); card.setLayout(new GridBagLayout()); GridBagConstraints c=new GridBagConstraints(); c.insets=new Insets(7,12,7,12); c.fill=GridBagConstraints.HORIZONTAL;
        c.gridx=0;c.gridy=0;c.gridwidth=2; card.add(UI.label("ADMIN LOGIN",22,UI.CYAN),c); c.gridwidth=1;
        c.gridy++; card.add(UI.label("Username",15,Color.WHITE),c); c.gridx=1; card.add(user,c); c.gridx=0;c.gridy++; card.add(UI.label("Password",15,Color.WHITE),c); c.gridx=1; pass.setEchoChar('*'); card.add(pass,c);
        c.gridx=0;c.gridy++;c.gridwidth=2; Button b=UI.button("LOGIN",UI.GREEN); card.add(b,c); c.gridy++; card.add(msg,c);
        g.gridx=0;g.gridy=0;center.add(card,g); add(center,BorderLayout.CENTER); setLocationRelativeTo(null); b.addActionListener(e->login()); addWindowListener(new WindowAdapter(){public void windowClosing(WindowEvent e){System.exit(0);}}); }
    void login(){ if((user.getText().equals("admin")&&pass.getText().equals("admin123"))||checkDB()){ dispose(); new Dashboard(); } else msg.setText("Invalid login. Try admin / admin123"); }
    boolean checkDB(){ try(Connection c=DB.get(); PreparedStatement p=c.prepareStatement("select user_id from users where username=? and password_hash=? and status='Active'")){p.setString(1,user.getText());p.setString(2,pass.getText());ResultSet r=p.executeQuery();return r.next();}catch(Exception e){return false;} }
    public static void main(String[] a){EventQueue.invokeLater(()->new Main().setVisible(true));}
}
class UI {
 static final Color NAVY=new Color(18,31,58), BG=new Color(236,242,250), CARD=new Color(35,52,86), CYAN=new Color(50,210,220), GREEN=new Color(60,210,130), ORANGE=new Color(255,170,70), RED=new Color(245,90,100), PURPLE=new Color(155,100,235), WHITE=new Color(245,248,255);
 static Label label(String s,int size,Color c){Label l=new Label(s,Label.CENTER);l.setFont(new Font("SansSerif",Font.BOLD,size));l.setForeground(c);return l;}
 static Panel card(int w,int h){Panel p=new Panel();p.setBackground(CARD);p.setPreferredSize(new Dimension(w,h));return p;}
 static Button button(String s,Color c){Button b=new Button(s);b.setFont(new Font("SansSerif",Font.BOLD,13));b.setBackground(c);b.setForeground(Color.BLACK);return b;}
 static void info(Component parent,String s){Dialog d=new Dialog((Frame)parent,"Message",true);d.setLayout(new BorderLayout());d.setSize(420,170);d.add(label(s,15,WHITE),BorderLayout.CENTER);Button b=button("OK",CYAN);d.add(b,BorderLayout.SOUTH);b.addActionListener(e->d.dispose());d.setLocationRelativeTo(parent);d.setVisible(true);}
 static TextField field(String value){TextField t=new TextField(value,20);t.setFont(new Font("SansSerif",Font.PLAIN,14));return t;}
}
