package biz.dfch.j.graylog2.plugin.alarm;

import org.graylog2.plugin.Message;
import org.graylog2.plugin.alarms.*;
import org.graylog2.plugin.alarms.callbacks.AlarmCallback;
import org.graylog2.plugin.alarms.callbacks.AlarmCallbackException;
import org.graylog2.plugin.configuration.*;
import org.graylog2.plugin.configuration.fields.*;
import org.graylog2.plugin.streams.Stream;
import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import org.graylog2.plugin.alarms.AlertCondition;
import org.graylog2.plugin.alarms.callbacks.AlarmCallback;
import org.graylog2.plugin.alarms.callbacks.AlarmCallbackConfigurationException;
import org.graylog2.plugin.alarms.callbacks.AlarmCallbackException;

import javax.script.ScriptContext;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.io.*;
import java.net.URI;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the plugin. Your class should implement one of the existing plugin
 * interfaces. (i.e. AlarmCallback, MessageInput, MessageOutput)
 */
public class dfchBizExecScript implements AlarmCallback
{
    private static final String DF_PLUGIN_NAME = "d-fens SCRIPT AlarmCallback";
    private static final String DF_PLUGIN_HUMAN_NAME = "biz.dfch.j.graylog2.plugin.alarm.execscript";
    private static final String DF_PLUGIN_DOC_LINK = "https://github.com/dfch/biz.dfch.j.graylog2.plugin.alarm.execscript";

    private static final String DF_SCRIPT_ENGINE = "DF_SCRIPT_ENGINE";
    private static final String DF_SCRIPT_PATH_AND_NAME = "DF_SCRIPT_PATH_AND_NAME";
    private static final String DF_DISPLAY_SCRIPT_OUTPUT = "DF_DISPLAY_SCRIPT_OUTPUT";
    private static final String DF_SCRIPT_CACHE_CONTENTS = "DF_SCRIPT_CACHE_CONTENTS";

    private boolean _isRunning = false;
    private Configuration _configuration;

    private static final ScriptEngineManager _scriptEngineManager = new ScriptEngineManager();
    private ScriptEngine _scriptEngine;
    private ScriptContext _scriptContext;
    StringWriter _stringWriter = new StringWriter();
    private File _file;

    private static final Logger LOG = LoggerFactory.getLogger(dfchBizExecScript.class);

    @Override
    public void initialize(final Configuration configuration)
    {
        try
        {
            String s = "*** " + DF_PLUGIN_NAME + "::initialize()";
            LOG.trace(s);

            _configuration = configuration;
            _isRunning = true;

            LOG.trace("DF_SCRIPT_ENGINE         : %s\r\n", _configuration.getString("DF_SCRIPT_ENGINE"));
            LOG.trace("DF_SCRIPT_PATH_AND_NAME  : %s\r\n", _configuration.getString("DF_SCRIPT_PATH_AND_NAME"));
            LOG.trace("DF_DISPLAY_SCRIPT_OUTPUT : %b\r\n", _configuration.getBoolean("DF_DISPLAY_SCRIPT_OUTPUT"));
            LOG.trace("DF_SCRIPT_CACHE_CONTENTS : %b\r\n", _configuration.getBoolean("DF_SCRIPT_CACHE_CONTENTS"));

            _file = new File(_configuration.getString("DF_SCRIPT_PATH_AND_NAME"));
            _scriptEngine = _scriptEngineManager.getEngineByName(_configuration.getString("DF_SCRIPT_ENGINE"));
            _scriptContext = _scriptEngine.getContext();

        }
        catch(Exception ex)
        {
            _isRunning = false;

            LOG.error("*** " + DF_PLUGIN_NAME + "::write() - Exception");
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public ConfigurationRequest getRequestedConfiguration()
    {
        final ConfigurationRequest configurationRequest = new ConfigurationRequest();

        configurationRequest.addField(new TextField
                        (
                                DF_SCRIPT_ENGINE
                                ,
                                "Script Engine"
                                ,
                                "javascript"
                                ,
                                "Specify the name of the script engine to use."
                                ,
                                ConfigurationField.Optional.NOT_OPTIONAL
                        )
        );
        configurationRequest.addField(new TextField
                        (
                                DF_SCRIPT_PATH_AND_NAME
                                ,
                                "Script Path"
                                ,
                                "/opt/graylog2/plugin/helloworld.js"
                                ,
                                "Specify the full path and name of the script to execute."
                                ,
                                ConfigurationField.Optional.NOT_OPTIONAL
                        )
        );
        configurationRequest.addField(new BooleanField
                        (
                                DF_DISPLAY_SCRIPT_OUTPUT
                                ,
                                "Show script output"
                                ,
                                false
                                ,
                                "Show the script output on the console."
                        )
        );
        configurationRequest.addField(new BooleanField
                        (
                                DF_SCRIPT_CACHE_CONTENTS
                                ,
                                "Cache script contents"
                                ,
                                true
                                ,
                                "Cache the contents of the script upon plugin initialisation."
                        )
        );
        return configurationRequest;
    }

    @Override
    public Map<String, Object> getAttributes()
    {
        return Maps.transformEntries(_configuration.getSource(), new Maps.EntryTransformer<String, Object, Object>()
        {
            @Override
            public Object transformEntry(String key, Object value) {
                return key + "-" + value;
            }
        });
    }

    @Override
    public void checkConfiguration() throws ConfigurationException
    {
        if (!_configuration.stringIsSet(DF_SCRIPT_ENGINE))
        {
            throw new ConfigurationException(DF_SCRIPT_ENGINE + ": Parameter validation FAILED. DF_SCRIPT_ENGINE must be not be null or empty.");
        }

        if (!_configuration.stringIsSet(DF_SCRIPT_PATH_AND_NAME))
        {
            throw new ConfigurationException(DF_SCRIPT_PATH_AND_NAME + ": Parameter validation FAILED. DF_SCRIPT_PATH_AND_NAME must be not be null or empty.");
        }
    }

    @Override
    public void call(final Stream stream, final AlertCondition.CheckResult result) throws AlarmCallbackException
    {
        LOG.trace("*** stream.getId: " + stream.getId());
        LOG.trace("*** stream.getTitle: " + stream.getTitle());
        LOG.trace("*** stream.getDescription: " + stream.getDescription());
        LOG.trace("*** stream.toString: " + stream.toString());

        LOG.trace("*** result.getResultDescription: " + result.getResultDescription());
        LOG.trace("*** result.toString: " + result.toString());
        LOG.trace("*** result.getTriggeredAt().toString: " + result.getTriggeredAt().toString());
        LOG.trace("*** result.getTriggeredCondition().getId: " + result.getTriggeredCondition().getId());
        LOG.trace("*** result.getTriggeredCondition().getBacklog: " + result.getTriggeredCondition().getBacklog());
        LOG.trace("*** result.getTriggeredCondition().getTypeString: " + result.getTriggeredCondition().getTypeString());
        LOG.trace("*** result.getTriggeredCondition().getCreatedAt().toString: " + result.getTriggeredCondition().getCreatedAt().toString());
        LOG.trace("*** result.getTriggeredCondition().getDescription: " + result.getTriggeredCondition().getDescription());

        if(!_isRunning) return;

        try
        {
            AlertCondition alertCondition = result.getTriggeredCondition();
            List<Message> messages = alertCondition.getSearchHits();

            _stringWriter.getBuffer().setLength(0);
            _scriptContext.setWriter(_stringWriter);
            _scriptEngine.put("stream", stream);
            _scriptEngine.put("result", result);
            _scriptEngine.put("messages", messages);
            
            if(!_configuration.getBoolean("DF_SCRIPT_CACHE_CONTENTS"))
            {
                _file = new File(_configuration.getString("DF_SCRIPT_PATH_AND_NAME"));
            }
            Reader _reader = new FileReader(_file);
            _scriptEngine.eval(_reader);
            if(_configuration.getBoolean("DF_DISPLAY_SCRIPT_OUTPUT"))
            {
                LOG.trace("%s\r\n", _stringWriter.toString());
            }

        }
        catch(FileNotFoundException ex)
        {
            String msg = "*** " + DF_PLUGIN_NAME + "::write() - FileNotFoundException";
            LOG.error(msg);
            ex.printStackTrace();
            throw new AlarmCallbackException(msg, ex);
        }
        catch(ScriptException ex)
        {
            String msg = "*** " + DF_PLUGIN_NAME + "::write() - ScriptException";
            LOG.error(msg);
            ex.printStackTrace();
            throw new AlarmCallbackException(msg, ex);
        }
        catch(Exception ex)
        {
            String msg = "*** " + DF_PLUGIN_NAME + "::write() - Exception";
            LOG.error(msg);
            ex.printStackTrace();
            throw new AlarmCallbackException(msg, ex);
        }
    }


    @Override
    public String getName()
    {
        return DF_PLUGIN_NAME;
    }

//    @Override
//    public String getHumanName()
//    {
//        return DF_PLUGIN_HUMAN_NAME;
//    }
//
//    @Override
//    public String getLinkToDocs()
//    {
//        return DF_PLUGIN_DOC_LINK;
//    }
}

/**
 *
 *
 * Copyright 2015 Ronald Rink, d-fens GmbH
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
