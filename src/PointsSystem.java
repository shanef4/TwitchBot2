import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class PointsSystem {

	public ArrayList<TwitchViewer> viewersT = new ArrayList<TwitchViewer>();
	public ArrayList<TwitchViewer> viewers = new ArrayList<TwitchViewer>();
	public DropboxUpload du = new DropboxUpload();
	private File f;
	private String content;
	private String[] u;

	public ArrayList<TwitchViewer> getViewersT() {
		return viewersT;
	}

	public void setViewersT(ArrayList<TwitchViewer> viewersT) {
		this.viewersT = viewersT;
	}

	public ArrayList<TwitchViewer> getViewers() {
		return viewers;
	}

	public void setViewers(ArrayList<TwitchViewer> viewers) {
		this.viewers = viewers;
	}

	public PointsSystem(){

	}
	
	public void cooldown(){
		if (this.viewers.size() > 0) {
			for (int i = 0; i < this.viewers.size(); i++)
			{
				if (((TwitchViewer)this.viewers.get(i)).getCD() > 0) {
					((TwitchViewer)this.viewers.get(i)).setCD(((TwitchViewer)this.viewers.get(i)).getCD() - 1);
				}
			}
		}
	}

	//reads a file with name fileName
	public String readFile(String fileName) throws IOException{
		BufferedReader br = new BufferedReader(new FileReader(fileName));
		try
		{
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();
			while (line != null)
			{
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}
			return sb.toString();
		}
		finally
		{
			br.close();
		}
	}

	//reads points.txt and adds users to list of total viewers with points
	public void getPoints() throws IOException{
		f = new File("points.txt");
		if(f.exists() && !f.isDirectory()){
			content = readFile("points.txt");
			if (content.length() > 0)
			{
				u = content.split("\n");
				for (int i = 0; i < u.length; i++)
				{
					String[] p = u[i].split(" ");
					TwitchViewer tmp = new TwitchViewer(p[0], Integer.parseInt(p[1]), Integer.parseInt(p[2]), Integer.parseInt(p[3]), 0);
					this.viewersT.add(tmp);
				}
			}
			delPoints();
		}
	}

	//delete text files upon startup after reading them
	public void delPoints(){
		boolean success = new File("points.txt").delete();
		if (success) {
			System.out.println("Points file successfully read and deleted, don't forget to save before closing the application window.");
		}
	}

	//adds points to each viewer in chat and reduces the cooldown on their roll
	public void updatePoints(){
		if (this.viewers.size() > 0) {
			for (int i = 0; i < this.viewers.size(); i++)
			{
				((TwitchViewer)this.viewers.get(i)).setPoints(((TwitchViewer)this.viewers.get(i)).getPoints() + ChibbotMain.getPPM());
				((TwitchViewer)this.viewers.get(i)).setLPoints(((TwitchViewer)this.viewers.get(i)).getLPoints() + ChibbotMain.getPPM());
			}
		}
	}
	//checks the points of a person with the username u
	public int checkPoints(String u){
		for (int i = 0; i < this.viewers.size(); i++) {
			if (u.equalsIgnoreCase(((TwitchViewer)this.viewers.get(i)).getName())) {
				return ((TwitchViewer)this.viewers.get(i)).getPoints();
			}
		}
		return 0;
	}

	//checks the points of a person with the username u
	public int checkReg(String u){
		for (int i = 0; i < this.viewers.size(); i++) {
			if (u.equalsIgnoreCase(((TwitchViewer)this.viewers.get(i)).getName())) {
				return this.viewers.get(i).getRV();
			}
		}
		return 0;
	}

	//checks the points of a person with the username u
	public void setReg(String u){
		for (int i = 0; i < this.viewers.size(); i++) {
			if (u.equalsIgnoreCase(((TwitchViewer)this.viewers.get(i)).getName())) {
				((TwitchViewer)this.viewers.get(i)).setRV(1);
			}
		}
	}

	//checks the points of a person with the username u
	public int checkLPoints(String u){
		for (int i = 0; i < this.viewers.size(); i++) {
			if (u.equalsIgnoreCase(((TwitchViewer)this.viewers.get(i)).getName())) {
				return ((TwitchViewer)this.viewers.get(i)).getLPoints();
			}
		}
		return 0;
	}

	public void changePoints(String u, int p){
		boolean c = false;

		for (int i = 0; i < this.viewers.size(); i++) {
			if (u.equalsIgnoreCase(this.viewers.get(i).getName())) {
				this.viewers.get(i).setPoints(p);
				c = true;
				JOptionPane.showMessageDialog(null, "Points have been changed successfully.");
			}
		}
		if(c == false){
			for (int i = 0; i < this.viewersT.size(); i++) {
				if (u.equalsIgnoreCase(this.viewersT.get(i).getName())) {
					this.viewersT.get(i).setPoints(p);
					c = true;
					JOptionPane.showMessageDialog(null, "Points have been changed successfully.");
				}
			}
		}
		if(c == false){
			JOptionPane.showMessageDialog(null, "User does not exist in the system.");
		}
	}

	//saves points and other information
	public boolean savePoints(){
		delPoints();
		for (int i = 0; i < this.viewers.size(); i++) {
			if (this.viewersT.indexOf(this.viewers.get(i)) != -1) {
				this.viewersT.set(this.viewersT.indexOf(this.viewers.get(i)), (TwitchViewer)this.viewers.get(i));
			}
		}
		try
		{
			StringBuilder fileContent = new StringBuilder();
			for (int i = 0; i < this.viewersT.size(); i++) {
				if(this.viewersT.get(i).getLPoints() > 5){
					fileContent.append(((TwitchViewer)this.viewersT.get(i)).getName() + " " + ((TwitchViewer)this.viewersT.get(i)).getPoints() + " " + ((TwitchViewer)this.viewersT.get(i)).getLPoints() + " " + ((TwitchViewer)this.viewersT.get(i)).getRV() + System.getProperty("line.separator"));
				}
			}
			FileWriter fstreamWrite = new FileWriter("points.txt", false);
			BufferedWriter bufferWritter = new BufferedWriter(fstreamWrite);
			bufferWritter.write(fileContent.toString());
			bufferWritter.close();

			du.uploadFile();

			return true;
		}
		catch (Exception e)
		{
			System.err.println("Error: " + e.getMessage());
		}
		return false;
	}
}
