package com.demandforce.bluebox.admin.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import com.demandforce.bluebox.admin.Constants;

public class AdminConfig
{
    private static final Logger LOGGER = Logger.getLogger(AdminConfig.class);
    private AdminConfig parent = null;
    private final Map<String, Object> config = new HashMap<String, Object>();
    private static AdminConfig adminConfig;

    public static AdminConfig getInstance()
    {
        if (adminConfig == null)
        {
            try
            {
                adminConfig = new AdminConfig(Constants.CONFIG_PATH);
            } catch (IOException e)
            {
                LOGGER.error("Encountered IOException while trying to read config file", e);
            } catch (SAXException e)
            {
                LOGGER.error("Encountered SAXException while parsing config file", e);
            }
        }
        return adminConfig;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String config)
    {
        Object entry = this.config.get(config);
        return (T) (entry == null && this.parent != null ? this.parent.get(config) : entry);
    }

    @SuppressWarnings("unchecked")
    private <T> T get(String config, T defaultValue)
    {
        Object item = this.get(config);
        return item == null ? defaultValue : (T) item;
    }

    private AdminConfig()
    {
    }

    private AdminConfig(File file) throws IOException, SAXException
    {
        this(new FileInputStream(file));
    }

    private AdminConfig(String resource) throws IOException, SAXException
    {
        this(AdminConfig.class.getResourceAsStream(resource));
    }

    private AdminConfig(InputStream in) throws IOException, SAXException
    {
        try
        {
            this.read(in);
        } finally
        {
            if (in != null)
                try
                {
                    in.close();
                } catch (IOException ignored)
                {
                    LOGGER.error("Encountered IOException while trying to close config file", ignored);
                }
        }
    }

    private void substitute(AdminConfig substitute)
    {
        this.config.clear();
        if (substitute != null)
            this.config.putAll(substitute.config);
    }

    private void read(InputStream in) throws IOException, SAXException
    {
        this.config.clear();
        XMLReader reader = XMLReaderFactory.createXMLReader();
        reader.setContentHandler(new ConfigHandler());
        reader.parse(new InputSource(in));
        if (LOGGER.isDebugEnabled())
        {
            LOGGER.debug("Admin Portal Config loaded keys:");
            for (Map.Entry<String, Object> entry : this.config.entrySet())
            {
                LOGGER.debug(">> " + entry.getKey() + " = " + entry.getValue());
            }
        }
    }

    class ConfigHandler extends DefaultHandler
    {
        private final StringBuilder builder = new StringBuilder();
        private String property;
        private Class<?> type;

        @Override
        public void characters(char[] ch, int start, int length) throws SAXException
        {
            this.builder.append(ch, start, length);
        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException
        {
            if ("config".equals(localName))
            {
                String parent = attributes.getValue("parent");
                if (parent != null)
                {
                    try
                    {
                        AdminConfig.this.parent = parent.startsWith("file:") ? new AdminConfig(new File(
                                parent.substring(0, 5))) : new AdminConfig('/' + parent);
                    } catch (IOException e)
                    {
                        throw new RuntimeException(e);
                    }
                }
            }
            if (!"property".equals(localName))
                return;
            this.builder.delete(0, this.builder.length());
            this.property = attributes.getValue("name");
            try
            {
                this.type = Class.forName(attributes.getValue("type"));
            } catch (ClassNotFoundException e)
            {
                LOGGER.error("Could not find the class " + attributes.getValue("type"), e);
            }
        }

        @Override
        @SuppressWarnings({ "unchecked", "rawtypes" })
        public void endElement(String uri, String localName, String qName) throws SAXException
        {
            if (!"property".equals(localName))
                return;
            String key = this.property;
            Object value = null;
            if (Enum.class.isAssignableFrom(this.type))
            {
                value = Enum.valueOf((Class<? extends Enum>) this.type, this.builder.toString());
            } else if (Integer.class == this.type)
            {
                value = Integer.valueOf(this.builder.toString());
            } else if (List.class == this.type)
            {
                value = Arrays.asList(this.builder.toString().split("\\s*,\\s*"));
            } else if (Map.class == this.type)
            {
                Map map = new HashMap<String, String>();
                for (String entry : this.builder.toString().split("\\s*,\\s*"))
                {
                    int idx = entry.indexOf('=');
                    if (idx == -1)
                        map.put(entry, null);
                    else
                        map.put(entry.substring(0, idx).trim(), entry.substring(idx + 1).trim());
                }
                value = map;
            } else
            {
                value = builder.toString();
            }
            AdminConfig.this.config.put(key, value);
        }
    }
}
