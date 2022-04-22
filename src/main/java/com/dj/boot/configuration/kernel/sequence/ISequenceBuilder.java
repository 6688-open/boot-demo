package com.dj.boot.configuration.kernel.sequence;


/**
 */
public interface ISequenceBuilder {

	public void createEndComponent();

	public void createSequence();

	public void createStartComponent();

	public ISequence getResult();

}