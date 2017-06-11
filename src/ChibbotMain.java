import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.dropbox.core.DbxException;

import PircBot.*;

public class ChibbotMain
{
	private String botName;
	private String botPassword;
	private String chanName;
	private String pointsperMin;
	private String pointsReg;
	private String chanceTotal;
	private String secsTotal;
	private static String e1;
	private static String e2;
	private static String e3;
	private static String e4;
	private static ArrayList<String> emotes = new ArrayList<String>();
	private static int ppm;
	private static Chibbot bot;
	private static int chance;
	private static int seconds;
	private static int pr;

	public static void main(String[] args) throws DbxException, IOException
	{
		ChibbotMain c = new ChibbotMain();
		c.connect();

		@SuppressWarnings("resource")
		ChibbyFrame f = new ChibbyFrame();
		f.init();
	}

	public void connect() throws DbxException, IOException
	{
		getSettings();

		bot = new Chibbot(getBotName());
		bot.setVerbose(true);
		try
		{
			bot.connect("irc.chat.twitch.tv", 6667, getBotPw());
		}
		catch (NickAlreadyInUseException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (IrcException e)
		{
			e.printStackTrace();
		}
		bot.joinChannel("#" + getChanName());
	}

	public String getBotName()
	{
		return this.botName;
	}

	public static Chibbot getBot()
	{
		return bot;
	}

	public String getBotPw()
	{
		return this.botPassword;
	}

	public String getChanName()
	{
		return this.chanName;
	}

	public static int getPPM()
	{
		return ppm;
	}

	public static int getChance()
	{
		return chance;
	}

	public static int getSeconds()
	{
		return seconds;
	}
	
	public static int getPR()
	{
		return pr;
	}
	
	public static ArrayList<String> getEmotes(){
		emotes.add(e1);
		emotes.add(e2);
		emotes.add(e3);
		emotes.add(e4);
		return emotes;
	}

	@SuppressWarnings("static-access")
	private void getSettings()
	{
		String filename = "settings.txt";
		String line = null;
		try
		{
			FileReader fr = new FileReader(filename);

			BufferedReader br = new BufferedReader(fr);
			while ((line = br.readLine()) != null) {
				if (line.indexOf("twitch account name:") != -1)
				{
					this.botName = line.substring("twitch account name:".length(), line.length());
					if (this.botName.substring(0, 1).equals(" ")) {
						this.botName = this.botName.substring(1, this.botName.length());
					}
				}
				else if (line.indexOf("twitch password:") != -1)
				{
					this.botPassword = line.substring("twitch password:".length(), line.length());
					if (this.botPassword.substring(0, 1).equals(" ")) {
						this.botPassword = this.botPassword.substring(1, this.botPassword.length());
					}
				}
				else if (line.indexOf("twitch channel to join:") != -1)
				{
					this.chanName = line.substring("twitch channel to join:".length(), line.length());
					if (this.chanName.substring(0, 1).equals(" ")) {
						this.chanName = this.chanName.substring(1, this.chanName.length());
					}
				}
				else if (line.indexOf("points per X:") != -1)
				{
					this.pointsperMin = line.substring("points per X:".length(), line.length());
					if (this.pointsperMin.substring(0, 1).equals(" ")) {
						this.pointsperMin = this.pointsperMin.substring(1, this.pointsperMin.length());
					}
					ppm = Integer.parseInt(this.pointsperMin);
				}
				else if (line.indexOf("points for regular:") != -1)
				{
					this.pointsReg = line.substring("points for regular:".length(), line.length());
					if (this.pointsReg.substring(0, 1).equals(" ")) {
						this.pointsReg = this.pointsReg.substring(1, this.pointsReg.length());
					}
					pr = Integer.parseInt(this.pointsReg);
				}
				else if (line.indexOf("chance of winning(1 out of):") != -1)
				{
					this.chanceTotal = line.substring("chance of winning(1 out of):".length(), line.length());
					if (this.chanceTotal.substring(0, 1).equals(" ")) {
						this.chanceTotal = this.chanceTotal.substring(1, this.chanceTotal.length());
					}
					chance = Integer.parseInt(this.chanceTotal);
				}
				else if (line.indexOf("number of seconds for points to increment (X):") != -1)
				{
					this.secsTotal = line.substring("number of seconds for points to increment (X):".length(), line.length());
					if (this.secsTotal.substring(0, 1).equals(" ")) {
						this.secsTotal = this.secsTotal.substring(1, this.secsTotal.length());
					}
					seconds = Integer.parseInt(this.secsTotal);
				}
				else if (line.indexOf("emote1:") != -1)
				{
					this.e1 = line.substring("emote1:".length(), line.length());
					if (this.e1.substring(0, 1).equals(" ")) {
						this.e1 = this.e1.substring(1, this.e1.length());
					}
				}
				else if (line.indexOf("emote2:") != -1)
				{
					this.e2 = line.substring("emote2:".length(), line.length());
					if (this.e2.substring(0, 1).equals(" ")) {
						this.e2 = this.e2.substring(1, this.e2.length());
					}
				}
				else if (line.indexOf("emote3:") != -1)
				{
					this.e3 = line.substring("emote3:".length(), line.length());
					if (this.e3.substring(0, 1).equals(" ")) {
						this.e3 = this.e3.substring(1, this.e3.length());
					}
				}
				else if (line.indexOf("emote4:") != -1)
				{
					this.e4 = line.substring("emote4:".length(), line.length());
					if (this.e4.substring(0, 1).equals(" ")) {
						this.e4 = this.e4.substring(1, this.e4.length());
					}
				}
			}
			br.close();
		}
		catch (FileNotFoundException ex)
		{
			System.out.println("File does not exist");
		}
		catch (IOException ex)
		{
			System.out.println("Error reading file");
		}
	}
}
