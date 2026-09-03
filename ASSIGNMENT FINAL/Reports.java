package smartcampus;
import java.awt.*;import java.sql.*;
public class Reports extends Panel{
 TextArea out=new TextArea();
 Reports(Frame f){
  setLayout(new BorderLayout(8,8));setBackground(UI.BG);
  add(UI.label("REPORTS & ANALYTICS",22,UI.NAVY),BorderLayout.NORTH);
  Panel p=new Panel(new GridLayout(0,1,5,5));
  Button a=UI.button("ZONE OCCUPANCY",UI.CYAN),b=UI.button("REVENUE",UI.GREEN),c=UI.button("TRAFFIC",UI.ORANGE),d=UI.button("VIOLATIONS",UI.RED),e=UI.button("PREDICTION",UI.PURPLE);
  p.add(a);p.add(b);p.add(c);p.add(d);p.add(e);add(p,BorderLayout.WEST);out.setEditable(false);add(out,BorderLayout.CENTER);
  a.addActionListener(x->zone());b.addActionListener(x->revenue());c.addActionListener(x->traffic());d.addActionListener(x->violations());e.addActionListener(x->prediction());
 }
 void zone(){run("select zone_name,total_slots,available_slots,occupied_slots,utilization_pct from v_zone_status",new String[]{"ZONE","TOTAL","FREE","OCC","UTIL%"});}
 void revenue(){try(Connection c=DB.get()){ResultSet r=c.createStatement().executeQuery("select coalesce(sum(amount),0) from payments where payment_status='Paid'");r.next();out.setText("TOTAL PAID REVENUE\nRs. "+r.getDouble(1));}catch(Exception e){out.setText(e.getMessage());}}
 void traffic(){run("select date(record_time),round(avg(congestion_level),1),count(*) from traffic_records group by date(record_time) order by date(record_time) desc limit 14",new String[]{"DATE","AVG CONGESTION","RECORDS"});}
 void violations(){run("select violation_type,count(*),coalesce(sum(fine_amount),0) from violations group by violation_type",new String[]{"TYPE","COUNT","FINES"});}
 void prediction(){try(Connection c=DB.get()){ResultSet r=c.createStatement().executeQuery("select coalesce(round(avg(occupied_slots/total_slots*100),1),0) from occupancy_records where recorded_at>=now()-interval 7 day");r.next();out.setText("7-DAY HISTORICAL OCCUPANCY ESTIMATE\nNext-period expected occupancy: "+r.getDouble(1)+"%\n\nMethod: recent historical average (database-backed baseline).");}catch(Exception e){out.setText(e.getMessage());}}
 void run(String sql,String[] h){try(Connection c=DB.get()){ResultSet r=c.createStatement().executeQuery(sql);StringBuilder s=new StringBuilder(String.join(" | ",h)).append('\n');while(r.next()){for(int i=1;i<=h.length;i++){if(i>1)s.append(" | ");s.append(r.getString(i));}s.append('\n');}out.setText(s.toString());}catch(Exception e){out.setText(e.getMessage());}}
}
