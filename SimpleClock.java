//package SimpleClock;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SimpleClock extends JFrame {
    
        Calendar calendar;
        SimpleDateFormat timeFormat;
        SimpleDateFormat dayFormat;
        SimpleDateFormat dateFormat;
    
        JLabel timeLabel;
        JLabel dayLabel;
        JLabel dateLabel;
        String time;
        String day;
        String date;

        JButton hoursButton;

        static boolean hrs12 = true;
        Thread thread1;
        Thread thread2;

        SimpleClock() {
            this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            this.setTitle("Digital Clock");
            this.setLayout(new FlowLayout());
            this.setSize(450, 320);
            this.setResizable(false);
    
            timeFormat = new SimpleDateFormat("hh:mm:ss a");
            dayFormat=new SimpleDateFormat("EEEE");
            dateFormat=new SimpleDateFormat("dd MMMMM, yyyy");
            timeLabel = new JLabel();
            timeLabel.setFont(new Font("SANS_SERIF", Font.PLAIN, 59));
            timeLabel.setBackground(Color.BLACK);
            timeLabel.setForeground(Color.WHITE);
            timeLabel.setOpaque(true);
            dayLabel=new JLabel();
            dayLabel.setFont(new Font("Ink Free",Font.BOLD,34));
    
            dateLabel=new JLabel();
            dateLabel.setFont(new Font("Ink Free",Font.BOLD,30));

            // margins... isn't this more like padding?
            Border margin = new EmptyBorder(30, 30, 30, 30);
            timeLabel.setBorder(margin);

            // button 12/24hr
            JButton hoursButton = new JButton("12 / 24 HR");
            hoursButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    hrs12 = !hrs12;
                    if (hrs12) {
                        thread2.interrupt();
                        setTimer();

                    } else {
                        thread1.interrupt();
                        setTimer24Hr();
                    }
                }
            });


            this.add(timeLabel);
            this.add(dayLabel);
            this.add(dateLabel);
            this.add(hoursButton);
            this.setVisible(true);
    
            setTimer();

        }

    
        public void setTimer() {
            timeFormat = new SimpleDateFormat("hh:mm:ss a");
             thread1 = new Thread(() -> {
                while (hrs12) {
                    //getTime() had local and GMT string methods. see if you can use that?
                    time = timeFormat.format(Calendar.getInstance().getTime());
                    timeLabel.setText(time);

                    day = dayFormat.format(Calendar.getInstance().getTime());
                    dayLabel.setText(day);

                    date = dateFormat.format(Calendar.getInstance().getTime());
                    dateLabel.setText(date);
                    System.out.println("thread 1");
                    try {
                        Thread.sleep(1000);
                    } catch (Exception e) {
                        e.getStackTrace();
                    }
                }
            });

            thread1.start();
        }

        public void setTimer24Hr(){
            timeFormat = new SimpleDateFormat("HH:mm:ss a");
            thread2 = new Thread(() -> {
            while (!hrs12) {
                //getTime() had local and GMT string methods. see if you can use that?
                time = timeFormat.format(Calendar.getInstance().getTime());
                timeLabel.setText(time);

                day = dayFormat.format(Calendar.getInstance().getTime());
                dayLabel.setText(day);

                date = dateFormat.format(Calendar.getInstance().getTime());
                dateLabel.setText(date);
                System.out.println("thread 2");

                try {
                    Thread.sleep(1000);
                } catch (Exception e) {
                    e.getStackTrace();
                }
            }
            });

            thread2.start();

        }
        public static void main(String[] args) {
            new SimpleClock();
        }
    }
