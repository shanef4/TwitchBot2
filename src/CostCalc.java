import java.util.ArrayList;
import java.util.Random;

import PircBot.User;

public class CostCalc {

	public ArrayList<TwitchViewer> viewersWon = new ArrayList<TwitchViewer>();
	public ArrayList<TwitchViewer> viewersDiscord = new ArrayList<TwitchViewer>();
	public ArrayList<TwitchViewer> viewersExtend = new ArrayList<TwitchViewer>();
	private int max = ChibbotMain.getChance();
	private int r1;
	private Random random;

	public ArrayList<TwitchViewer> getViewersWon() {
		return viewersWon;
	}

	public void setViewersWon(ArrayList<TwitchViewer> viewersWon) {
		this.viewersWon = viewersWon;
	}

	public ArrayList<TwitchViewer> getViewersDiscord() {
		return viewersDiscord;
	}

	public void setViewersDiscord(ArrayList<TwitchViewer> viewersDiscord) {
		this.viewersDiscord = viewersDiscord;
	}

	public ArrayList<TwitchViewer> getViewersExtend() {
		return viewersExtend;
	}

	public void setViewersExtend(ArrayList<TwitchViewer> viewersExtend) {
		this.viewersExtend = viewersExtend;
	}

	public CostCalc(){

	}

	//rolls a random number
	public String viewerRoll(User u, int g){
		ArrayList<TwitchViewer> viewers = ChibbotMain.getBot().ps.getViewers();
		TwitchViewer r = null;
		for (int c = 0; c < viewers.size(); c++) {
			if (((TwitchViewer)viewers.get(c)).getName().equalsIgnoreCase(u.getNick())) {
				r = (TwitchViewer)viewers.get(c);
			}
		}
		if (r.getPoints() >= g){
			if(r.getCD() == 0){
				r.setPoints(r.getPoints() - g);
				r.setCD(5);

				random = new Random();
				r1 = random.nextInt(this.max) + 1;

				if (r1 == 35 || r1 == 73){
					r.setPoints(0);;
					return "Your number was: " + r1 + ". You've lost all your points. Gg.";
				}
				if(r1 == 69 || r1 == 99 || r1 == 100){
					r.setPoints(r.getPoints() + (g * 3));
					return "Your number was: " + r1 + ". You won " + (g*3) + " points, congratulations!";
				}
				if(r1 > 60){
					r.setPoints(r.getPoints() + (g * 2));
					return "Your number was: " + r1 + ". You won " + (g*2) + " points, congratulations!";
				}
				return "Your number was: " + r1 + ". You lost " + g + " points. Gg.";
			}
			else{
				return "Sorry, your gamble is on cooldown, try again in ~30 seconds!";
			}
		}
		return 
				"Sorry " + u.getNick() + " but you don't have enough points for this! " + "You need at least " + g + "!";
	}
	//extends the stream by 30 minutes and removes points
	public String extendStream(String username){
		ArrayList<TwitchViewer> viewers = ChibbotMain.getBot().ps.getViewers();
		boolean check = false;
		TwitchViewer r = null;
		for (int c = 0; c < viewers.size(); c++) {
			if (((TwitchViewer)viewers.get(c)).getName().equalsIgnoreCase(username)) {
				r = (TwitchViewer)viewers.get(c);
			}
		}
		if(viewersExtend.size() < 2){
			for(int i = 0; i < viewersExtend.size(); i++){
				if(viewersExtend.get(i).getName().equalsIgnoreCase(username)){
					check = true;
				}
			}
			if(check == false){
				if (r.getPoints() >= 800){
					r.setPoints(r.getPoints() - 800);
					this.viewersExtend.add(r);
					return "You have added an extra 30 minutes to the stream " + username + ", thanks for the support!";

				}
				else{
					return 
							"Sorry " + username + " but you don't have enough points for this! You need at least 800!";
				}
			}
			else{
				return 
						"Sorry " + username + " you have already bought a half an hour of extra time for this stream!";
			}
		}
		else{
			return 
					"Sorry " + username + " but there has already been an hour of extra time bought for this stream! Get here early next time and you can use the extension right away. :)";
		}
	}

	//get extra time added to stream
	public String getExtraTime(){
		if(viewersExtend.size() == 0){
			return "There has been no extra time purchased for this stream.";
		}
		else if(viewersExtend.size() == 1){
			return viewersExtend.get(0).getName() + " has purchased extra stream time. Chibbun must stream for 30 more minutes than scheduled!";
		}
		else if(viewersExtend.size() == 2){
			return viewersExtend.get(0).getName() + " and " + viewersExtend.get(1).getName() + " have purchased extra stream time. Chibbun must stream for one more hour than scheduled!";
		}
		else{
			return "Command broke, rip.";
		}
	}
}
