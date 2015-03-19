package com.shanbay.words.fsm;

public abstract class State
{
  public abstract State handleSignal(StateMachine.Signal paramSignal);
}