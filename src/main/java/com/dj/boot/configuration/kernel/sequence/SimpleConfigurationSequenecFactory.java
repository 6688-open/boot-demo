package com.dj.boot.configuration.kernel.sequence;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

/**
 */
public abstract class SimpleConfigurationSequenecFactory implements IEngineFactory {

	protected static final Logger LOGGER = LogManager.getLogger(SimpleConfigurationSequenecFactory.class);

	private Map<String,ISequence> sequenceMap;

	public Map<String, ISequence> getSequenceMap() {
		return sequenceMap;
	}

	public void setSequenceMap(Map<String, ISequence> sequenceMap) {
		this.sequenceMap = sequenceMap;
	}
}