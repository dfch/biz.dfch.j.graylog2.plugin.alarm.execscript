// when this script is executed the following variables are available:

// message  --- a Graylog Message object
// messages --- a List<> of Graylog messages (the search hits returned by the alert condition)
// stream   --- the stream the alert condition is associated with
// result   --- the AlertCondition.CheckResult containing the result description
//              from there you can get the AlertCondition

// prints the whole message
print("AlarmCallback: Now this is a message: " + message + "\r\n");

// printing the message source
print("AlarmCallback: message.source: " + message.getSource() + "\r\n");

// printing the message source
print("AlarmCallback: message.timestamp: " + message.getTimestamp() + "\r\n");

// printing the stream title
print("AlarmCallback: stream.title: " + stream.getTitle() + "\r\n");

// printing the description of the alarm condition
print("AlarmCallback: result.description: " + result.getResultDescription() + "\r\n");

// now you can invoke arbitrary system commands via 
// https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Shell#runCommand
