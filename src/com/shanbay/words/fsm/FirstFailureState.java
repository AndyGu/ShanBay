package com.shanbay.words.fsm;

public class FirstFailureState extends State
{
  public State handleSignal(StateMachine.Signal paramSignal)
  {
    if (paramSignal == StateMachine.Signal.SPELL_INCORRECT)
    	return new SecondFailureState();
    if (paramSignal == StateMachine.Signal.SPELL_ORIGINALFORM)
          return new MatchOriginalFormState();
    if (paramSignal == StateMachine.Signal.SPELL_CORRECT);
          return new EndState();
  }
}