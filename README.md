biz.dfch.j.graylog2.plugin.alarm.execscript
============================================

Plugin: biz.dfch.j.graylog2.plugin.alarm.execscript

d-fens GmbH, General-Guisan-Strasse 6, CH-6300 Zug, Switzerland

This Graylog2 AlarmCallback Plugin lets you run arbitrary scripts on a Graylog2 node as a result of an alarm condition.

See [Creating a Graylog2 Output Plugin](http://d-fens.ch/2015/01/07/howto-creating-a-graylog2-output-plugin/) for further description on how to create plugins.

You can [download the binary](https://drone.io/github.com/dfch/biz.dfch.j.graylog2.plugin.alarm.execscript/files) [![Build Status](https://drone.io/github.com/dfch/biz.dfch.j.graylog2.plugin.alarm.execscript/status.png)](https://drone.io/github.com/dfch/biz.dfch.j.graylog2.plugin.alarm.execscript/latest) at our [drone.io](https://drone.io/github.com/dfch) account, which gets built every time we commit something to the master branch of the repository.

Usage
-----

When executing scripts you will find a global list of `messages` inside your script session that you can use to get the contents of the message. As you can see in the [example script](https://github.com/dfch/biz.dfch.j.graylog2.plugin.alarm.execscript/blob/master/src/main/resources/bizDfchMessageAlarmScript.js) the `messages` variable is *just there* as a POJO object and has all the properties you would be able to use from Java.

In addition the [stream and result](src/main/java/biz/dfch/j/graylog2/plugin/alarm/dfchBizExecScript.java#L194) are passed as variables to the script as well.

So to get the source of the message you could type something like

    print(message.getSource());

Running a system command can be achieved by using [Rhino/Shell #runCommand](https://developer.mozilla.org/en-US/docs/Mozilla/Projects/Rhino/Shell#runCommand).


Getting started for users
-------------------------

This project is using Maven and requires Java 7 or higher.

* Clone this repository.
* Run `mvn package` to build a JAR file.
* Optional: Run `mvn jdeb:jdeb` and `mvn rpm:rpm` to create a DEB and RPM package respectively.
* Copy generated jar file in target directory to your Graylog2 server plugin directory.
* Restart the Graylog2 server.
