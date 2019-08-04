import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class Tweeter {
	
	private Twitter twitter;
	private long lastMentionId;

	public Tweeter() throws TwitterException {
		twitter = TwitterFactory.getSingleton();
		lastMentionId = twitter.getMentionsTimeline().get(0).getId();
	}

	/**
	 * This method replies to a given Tweet with a given message
	 * 
	 * @param String a message you wish to Tweet out
	 * @param Status the status you wish to reply to
	 * @throws TwitterException 
	 */
	public void tweetOut(String message, Status theStatus) throws TwitterException {
		//creates StatusUpdate object with message as the parameter
		StatusUpdate reply = new StatusUpdate("@" + theStatus.getUser().getScreenName() + " " + message);
		
		//sets tweet to reply to the tweet where the bot was called
		reply = reply.inReplyToStatusId(theStatus.getId());

		//tweet the message in reply to the user
		twitter.updateStatus(reply);
	}
	
	/**
	 * This method returns a ResponseList of Statuses referring to mentions
	 * not yet responded to
	 * 
	 * @throws TwitterException
	 */
	public ResponseList<Status> getNewMentions() throws TwitterException {
		//new Paging object used for a reference of what mentions have been replied to already
		Paging page = new Paging(lastMentionId);
		
		//makes ResponseList of mentions
		ResponseList<Status> responses = twitter.getMentionsTimeline(page);
		
		//sets lastMentionId to the last Tweet (most recent) so tweets aren't replied to multiple times
		if (!responses.isEmpty()) {
			lastMentionId = responses.get(0).getId(); 
		}
		
		return responses;
	}

}
