package com.shanbay.words.fsm;

public class MatchOriginalFormState extends State
{
  public State handleSignal(StateMachine.Signal paramSignal)
  {
    return new EndState();
  }
}