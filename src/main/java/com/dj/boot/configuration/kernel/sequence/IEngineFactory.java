package com.dj.boot.configuration.kernel.sequence;


/**
 */
public interface IEngineFactory {

	public ISequence create(String sequenceId);

}