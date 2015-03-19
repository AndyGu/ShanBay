package com.shanbay.words.model;

public class ReviewData
{
  private ExampleData exampleData;
  private NoteData noteData;
  private RootsData rootsData;
  private VocabularyData vocabularyData;

  public ExampleData getExamples()
  {
    return this.exampleData;
  }

  public NoteData getNotes()
  {
    return this.noteData;
  }

  public RootsData getRoots()
  {
    return this.rootsData;
  }

  public VocabularyData getVocabulary()
  {
    return this.vocabularyData;
  }

  public ReviewData setExampleData(ExampleData paramExampleData)
  {
    this.exampleData = paramExampleData;
    return this;
  }

  public ReviewData setNoteData(NoteData paramNoteData)
  {
    this.noteData = paramNoteData;
    return this;
  }

  public ReviewData setRootsData(RootsData paramRootsData)
  {
    this.rootsData = paramRootsData;
    return this;
  }

  public ReviewData setVocabularyData(VocabularyData paramVocabularyData)
  {
    this.vocabularyData = paramVocabularyData;
    return this;
  }
}