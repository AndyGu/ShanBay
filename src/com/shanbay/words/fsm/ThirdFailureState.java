package com.shanbay.words.fsm;

public class ThirdFailureState extends State
{
  public State handleSignal(StateMachine.Signal paramSignal)
  {
    if (paramSignal == StateMachine.Signal.SPELL_CORRECT)
    	return new EndState();
    if ((paramSignal == StateMachine.Signal.SPELL_INCORRECT) || (paramSignal != StateMachine.Signal.SPELL_ORIGINALFORM));
    	return new MatchOriginalFormState();
  }
}