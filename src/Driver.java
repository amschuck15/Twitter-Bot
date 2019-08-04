import java.io.IOException;
import java.util.concurrent.TimeUnit;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;

public class Driver {

	public static void main(String[] args) {

		try {
			//attempt to make a new Tweeter object
			Tweeter theTweet = new Tweeter();
			//while true so bot runs indefinitely
			while (true) {
				ResponseList<Status> questionStatus = theTweet.getNewMentions();
				Wolfram theQuestion;
				for (Status status : questionStatus) {
					theQuestion = new Wolfram(status.getText().substring(14));
					String message = theQuestion.getAnswer();
					theTweet.tweetOut(message, status);
				}
				TimeUnit.SECONDS.sleep(25); // sleep for 25 seconds in compliance with Twitter API restrictions
			}
		} catch (TwitterException e) {
			System.out.println("Twitter is down.");
		} catch (IOException e) {
			System.out.println("There was an issue connecting to Wolfram Alpha");
		} catch (InterruptedException e) {
			System.out.println("Thread was interupted");
		}

	}

}