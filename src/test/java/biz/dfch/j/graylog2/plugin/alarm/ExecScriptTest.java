package biz.dfch.j.graylog2.plugin.alarm;

import org.graylog2.plugin.alarms.callbacks.AlarmCallbackConfigurationException;
import org.graylog2.plugin.database.validators.Validator;
import org.graylog2.plugin.outputs.MessageOutputConfigurationException;
import org.graylog2.plugin.streams.Output;
import org.graylog2.plugin.streams.StreamRule;
import org.joda.time.DateTime;
import org.junit.*;
import org.junit.experimental.categories.Category;
import org.junit.runners.MethodSorters;

import java.io.IOException;
import java.net.ProtocolException;
import java.net.URISyntaxException;
import java.security.Key;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.*;

import org.graylog2.plugin.Message;

import org.graylog2.plugin.configuration.Configuration;
import org.graylog2.plugin.configuration.ConfigurationRequest;
import org.graylog2.plugin.configuration.fields.BooleanField;
import org.graylog2.plugin.configuration.fields.ConfigurationField;
import org.graylog2.plugin.configuration.fields.TextField;
import org.graylog2.plugin.streams.Stream;
import static org.junit.Assert.assertEquals;

/**
 * Created by root on 3/31/15.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class ExecScriptTest
{
    private Message message;

    @BeforeClass
    public static void BeforeClass()
    {
        System.out.println("BeforeClass");
    }
    @Before
    public void Before()
    {
        System.out.println("Before");
    }

    @After
    public void After() throws Throwable {

        System.out.println("After");
    }

    // show that the script engine can properly instantiate a python scriptEngine
    @Test
    public void doPythonTest() throws ProtocolException, IOException, AlarmCallbackConfigurationException
    {
        String DF_SCRIPT_ENGINE = "DF_SCRIPT_ENGINE";
        String DF_SCRIPT_PATH_AND_NAME = "DF_SCRIPT_PATH_AND_NAME";
        String DF_DISPLAY_SCRIPT_OUTPUT = "DF_DISPLAY_SCRIPT_OUTPUT";
        String DF_SCRIPT_CACHE_CONTENTS = "DF_SCRIPT_CACHE_CONTENTS";

        Map<String, Object> map = new HashMap<>();
        map.put("DF_SCRIPT_ENGINE", "python");
        map.put("DF_SCRIPT_PATH_AND_NAME", "/opt/graylog/plugin/pythontest.py");
        map.put("DF_DISPLAY_SCRIPT_OUTPUT", true);
        map.put("DF_SCRIPT_CACHE_CONTENTS", false);

        class TestStream implements Stream
        {
            String id = "id";
            String title = "title";
            String description = "description";
            Boolean disabled = false;
            String contentPack = null;
            Boolean isPaused = false;

            @Override
            public Map<String, Validator> getEmbeddedValidations(String s) {
                return new HashMap<String, Validator>();
            }

            @Override
            public Map<String, Validator> getValidations() {
                return new HashMap<String, Validator>();
            }

            @Override
            public Map<String, Object> getFields() {
                return new HashMap<String, Object>();
            }

            public String getId()
            {
                return id;
            }

            public String getTitle()
            {
                return title;
            }

            public String getDescription()
            {
                return description;
            }

            public Boolean getDisabled()
            {
                return disabled;
            }

            public String getContentPack()
            {
                return contentPack;
            }

            public void setTitle(String var1)
            {
                title = var1;
            }

            public void setDescription(String var1)
            {
                description = var1;
            }

            public void setDisabled(Boolean var1)
            {
                disabled = var1;
            }

            public void setContentPack(String var1)
            {
                contentPack = var1;
            }

            public Boolean isPaused()
            {
                return isPaused;
            }

            public Map<String, List<String>> getAlertReceivers()
            {
                return new HashMap<String, List<String>>();
            }

            @Override
            public Map<String, Object> asMap() {
                return new HashMap<String, Object>();
            }

            public Map<String, Object> asMap(List<StreamRule> var1)
            {
                return new HashMap<String, Object>();
            }

            public String toString()
            {
                return "myMessage";
            }

            public List<StreamRule> getStreamRules()
            {
                return new ArrayList<StreamRule>();
            }

            public Set<Output> getOutputs()
            {
                return new HashSet<Output>();
            }
        }

        message = new Message("my Message", "mySource", DateTime.now());
        message.addField("myField", "myValue");

        Configuration configuration = new Configuration(map);

        dfchBizExecScript execScript = new dfchBizExecScript();
        execScript.initialize(configuration);

        assertEquals(true, true);
    }

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
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
