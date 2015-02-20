package biz.dfch.j.graylog2.plugin.alarm;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import org.graylog2.plugin.Plugin;
import org.graylog2.plugin.PluginMetaData;
import org.graylog2.plugin.PluginModule;

/**
 * Implement the Plugin interface here.
 */
public class dfchBizExecScriptPlugin implements Plugin
{
    @Override
    public PluginMetaData metadata() {
        return new dfchBizExecScriptMetadata();
    }
    @Override
    public Collection<PluginModule> modules ()
    {
        //return Lists.newArrayList((PluginModule) new dfchBizExecScriptModule());
        return Arrays.<PluginModule>asList(new dfchBizExecScriptModule());
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
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */
