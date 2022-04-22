package com.dj.boot.configuration.kernel.sequence;


/**
 */
public class SpringConfigurationSequenceFactory extends SimpleConfigurationSequenecFactory {

	public ISequence create(String id){

		return getSequenceMap().get(id);
	}

}