package com.shanbay.words.fsm;

public class StateMachine
{
  private static StateMachine fsm = null;
  private State currentState = null;

  private StateMachine(State paramState)
  {
    this.currentState = paramState;
  }

  public static StateMachine getInstance()
  {
    if (fsm == null)
      fsm = new StateMachine(new StartState());
    return fsm;
  }

  public void InitState()
  {
    this.currentState = new StartState();
  }

  public State getCurrentState()
  {
    return this.currentState;
  }

  public void handleSignal(Signal paramSignal)
  {
    if (this.currentState != null)
    {
      this.currentState = this.currentState.handleSignal(paramSignal);
      return;
    }
    this.currentState = new StartState();
  }

  public static enum Signal{
	  SPELL_CORRECT, SPELL_INCORRECT, SPELL_ORIGINALFORM
  }
}