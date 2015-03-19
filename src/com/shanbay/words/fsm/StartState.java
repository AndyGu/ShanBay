package com.shanbay.words.fsm;

public class StartState extends State
{
  public State handleSignal(StateMachine.Signal paramSignal)
  {
    if (paramSignal == StateMachine.Signal.SPELL_CORRECT)
    	return new EndState();
    if (paramSignal == StateMachine.Signal.SPELL_INCORRECT)
        return new FirstFailureState();
    if (paramSignal == StateMachine.Signal.SPELL_ORIGINALFORM);
    	return new MatchOriginalFormState();
  }
}