
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class IIUIClock extends JPanel implements ActionListener,ItemListener{

    JPanel  uppanel,dpanel,bpanel, mainpanel;

    JFrame d;
    JTextArea hour,min,sec;
    JRadioButton yes,no;
    ButtonGroup group,mgroup;
    JButton start,stop,reset;
    
    int checkTransport;
    int status,selection=0;
    public JMenuBar bar;
    public JMenu file;
    public JMenuItem  exit,thirty,sixty;
    
    String display="";
    MyTimeThread thread;


  public IIUIClock(){


    d= new JFrame();
    d.setResizable(false);
    status=0;
    d.setTitle("Quiz Clock");

      d.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE );
      int width=360,height=230;
      Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
      int x= (screen.width-width)/2;
      int y= (screen.height-height)/2;
      d.setBounds(x,y,width,height);
      this.setBackground(new Color(202,215,237));
      this.setLayout(new BorderLayout());
    d.getContentPane().add(new JScrollPane(this));

/***********************************************************/
  mgroup= new ButtonGroup();
  bar= new JMenuBar();
    file= new JMenu("File");
    file.setMnemonic('F');

    thirty= new JRadioButtonMenuItem("30 Sec");
    thirty.setSelected(true);
    mgroup.add(thirty);
    file.add(thirty);
    thirty.addActionListener(this);

    sixty= new JRadioButtonMenuItem("60 Sec");
    mgroup.add(sixty);
    sixty.setSelected(false);
    file.add(sixty);
    sixty.addActionListener(this);

    exit= new JMenuItem(" Exit",new ImageIcon("icon/exit.gif"));
    file.add(exit);
    exit.addActionListener(this);
  bar.add(file);
  this.add(bar);
/****************************************/
  d.setJMenuBar(bar);

    mainpanel= new JPanel();
    mainpanel.setLayout(new BorderLayout());
    mainpanel.setBackground(new Color(202,215,237));

    uppanel= new JPanel();
    uppanel.setBackground(Color.black);
    uppanel.setBorder(BorderFactory.createLineBorder(new Color(204,255,102),4));
    uppanel.setLayout(new GridLayout(1,5));

    hour= new JTextArea(1,1);
    hour.setEditable(false);
      hour.setBackground(Color.black);
      hour.setFont( new Font( "Times New Roman", Font.BOLD,90));
    hour.setForeground(Color.green);
    hour.setText("00:");
    uppanel.add(hour);

    min= new JTextArea(1,1);
    min.setEditable(false);
    min.setBackground(Color.black);
    min.setFont( new Font( "Times New Roman", Font.BOLD,90));
    min.setForeground(Color.green);
    min.setText("00:");
    uppanel.add(min);

    sec= new JTextArea(1,1);
    sec.setEditable(false);
    sec.setBackground(Color.black);
    sec.setFont( new Font( "Times New Roman", Font.BOLD,90));
    sec.setForeground(Color.green);
    sec.setText("00");
    uppanel.add(sec);
    
    mainpanel.add(uppanel,BorderLayout.NORTH);
/*********************************************************/
    mainpanel.add(new JSeparator(),BorderLayout.CENTER);

    dpanel= new JPanel();
    dpanel.setBackground(new Color(202,215,237));
    dpanel.setLayout(new GridLayout(2,3,5,5));


    dpanel.add(new JLabel("Select Clock",JLabel.LEFT));

    yes = new JRadioButton("Analog",false);
    yes.setBackground(new Color(202,215,237));
    yes.setForeground(Color.blue);
    yes.addActionListener(this);
    yes.addItemListener(this);

    no = new JRadioButton("Digital",true);
    no.setBackground(new Color(202,215,237));
    no.setForeground(Color.blue);
    no.addActionListener(this);
    no.addItemListener(this);

    group = new ButtonGroup();
    group.add(yes);
    group.add(no);
    dpanel.add(yes);
    dpanel.add(no);
    checkTransport=0;

    start = new JButton(" Start  ",new ImageIcon("icon/start.gif"));
    start.setFont(new Font("Serif",Font.BOLD,14));
    start.setBorder(BorderFactory.createRaisedBevelBorder());
    start.addActionListener(this);
    start.setEnabled(true);
    start.setBackground(Color.green);
    dpanel.add(start);

    stop =  new JButton(" Stop  ",new ImageIcon("icon/stop.gif"));
    stop.setFont(new Font("Serif",Font.BOLD,14));
    stop.setBorder(BorderFactory.createRaisedBevelBorder());
    stop.addActionListener(this);
    stop.setEnabled(false);
    stop.setBackground(Color.red);
    dpanel.add(stop);

    reset = new JButton(" Reset  ",new ImageIcon("icon/reset.gif"));
    reset.setFont(new Font("Serif",Font.BOLD,14));
    reset.setBorder(BorderFactory.createRaisedBevelBorder());
    reset.addActionListener(this);
    reset.setEnabled(false);
    reset.setBackground(Color.red);
    dpanel.add(reset);

    bpanel= new JPanel();
    bpanel.setBackground(new Color(202,215,237));
    bpanel.add(dpanel);
    mainpanel.add(bpanel,BorderLayout.SOUTH);
    this.add(mainpanel,BorderLayout.NORTH);


    d.getContentPane().add(this);
    d.show();
  }

/*****************************************************/

  public void itemStateChanged(ItemEvent ie)
  {

    if(ie.getSource() == yes)
      {
         checkTransport=1;

      }
    if(ie.getSource() == no)
      {
         checkTransport=1;
      }
  }
  
  
  public void actionPerformed(ActionEvent e)
  {

      if(e.getSource() == start)
      {
        status=1;
        thread=new MyTimeThread(this);
        thread.start();

        start.setEnabled(false);
        start.setBackground(Color.red);
        stop.setEnabled(true);
        stop.setBackground(Color.green);
        reset.setEnabled(false);
        reset.setBackground(Color.red);
      }

    if(e.getSource() == stop)
        
       {
        status=2;
        thread.stop();

        start.setEnabled(false);
        start.setBackground(Color.red);
        stop.setEnabled(false);
        stop.setBackground(Color.red);
        reset.setEnabled(true);
        reset.setBackground(Color.green);
      }

    if(e.getSource() == reset)
      {
        hour.setText("00:");
        min.setText("00:");
        sec.setText("00");
           status=0;

        start.setEnabled(true);
        start.setBackground(Color.green);
        stop.setEnabled(false);
        stop.setBackground(Color.red);
        reset.setEnabled(false);
        reset.setBackground(Color.red);

      }
    if(e.getSource() == thirty){

        selection=0;

      }
    if(e.getSource() == sixty){
          selection=1;

      }

    if(e.getSource() == exit){
      System.exit(0);
      }
    }

public static void main (String []agru){

    IIUIClock regform = new IIUIClock();
  }
}
/*****************************************************/
class MyTimeThread extends Thread
{
  IIUIClock iiu;
  int minute,hor;
  String display;
  String second;
  int counter;
  
  MyTimeThread(IIUIClock iiu)
  {
    System.out.println("thread function called");
    this.iiu=iiu;
    minute=0;
    hor=0;
    counter=0;
    display="";
    second="";

  }

  public void run()
  {
    System.out.println(iiu.status);
    if(iiu.status==1)
    {
      System.out.println("I am calling thread");
      int sleepTime=1000;
      try
      {
        System.out.println("I am just calling thread");
        while(true)
        {
          counter++;
          System.out.println("Counter "+counter);
          if(counter<=59)
          {
            if(counter<=9)
            {
              iiu.display="0";
              iiu.display+=Integer.toString(counter);
            }
            else
            {
              /*********30 Second Break**********/
              if(iiu.selection==0)
              {
                //if(counter==15)
                 // iiu.sound4.play();
                if(counter==31)
                {
                  for(int i=0; i<3; i++)
                  //iiu.sound1.play();
                  iiu.reset.setEnabled(true);
                  iiu.reset.setBackground(Color.green);
                  iiu.stop.setEnabled(false);
                  iiu.stop.setBackground(Color.red);
                  this.destroy();

                }
              }
              /********************************/
              iiu.display=Integer.toString(counter);
            }
            iiu.sec.setText(iiu.display);

          }
          else
          {
            minute++;
            iiu.sec.setText("00");

            if(minute<=59)
            {
              if(minute<=9)
              {
                iiu.display="0";
                iiu.display+=Integer.toString(minute);
                //iiu.display+=":";
              }
              else
                  iiu.display=Integer.toString(minute);
                  //iiu.display=":";
              iiu.min.setText(iiu.display+":");
              
              /********60 second break********/
              if(counter==60)
              {
                for(int j=0;j<3;j++)
                //iiu.sound1.play();
                iiu.reset.setEnabled(true);
                iiu.reset.setBackground(Color.green);
                iiu.stop.setEnabled(false);
                iiu.stop.setBackground(Color.red);
                this.destroy();
              }
              /*****************************/
              counter=0;

            }
            else
            {
              minute=0;
              hor++;
              iiu.min.setText("00:");
              if(hor<=23)
              {
                if(hor<=9)
                {
                  iiu.display="0";
                  iiu.display+=Integer.toString(hor);
                  //iiu.display+=":";
                }
                else

                  iiu.display=Integer.toString(hor);
              iiu.hour.setText(iiu.display+":");
              }
              else
              {
              iiu.hour.setText("00");
              hor=0;
              }

            }
          }

          Thread.sleep(sleepTime);
          System.out.println("I am going to sleep");
        }
      }
      catch(InterruptedException ex)
      {
        System.out.println("I am exception");
      }
    }
    else if(iiu.status==2)
    {
      this.destroy();
    }

  }
}
