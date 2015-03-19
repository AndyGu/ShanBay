package com.shanbay.words.fsm;

public class EndState extends State
{
  public State handleSignal(StateMachine.Signal paramSignal)
  {
    return this;
  }
}