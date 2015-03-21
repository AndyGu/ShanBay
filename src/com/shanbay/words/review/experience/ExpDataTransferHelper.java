package com.shanbay.words.review.experience;

import com.shanbay.words.model.Example;
import com.shanbay.words.model.ExampleContent;
import com.shanbay.words.model.ExampleData;
import com.shanbay.words.model.ExpReview;
import com.shanbay.words.model.Roots;
import com.shanbay.words.model.RootsContent;
import com.shanbay.words.model.RootsData;
import com.shanbay.words.model.VocabularyData;
import java.util.ArrayList;
import java.util.Iterator;

public class ExpDataTransferHelper
{
  public ExampleData getExampleData(ExpReview paramExpReview)
  {
    ExampleData localExampleData = new ExampleData();
    ArrayList<ExampleContent> list = new ArrayList<ExampleContent>();
    Iterator<Example> localIterator = paramExpReview.sysExamples.iterator();
    while (localIterator.hasNext())
    {
      Example localExample = (Example)localIterator.next();
      ExampleContent mExampleContent = new ExampleContent();
      mExampleContent.setId(localExample.id)
      	.setTranslation(localExample.translation)
      	.setAnnotation(localExample.annotation, localExample.first, localExample.last, localExample.mid);
      list.add(mExampleContent);
    }
    localExampleData.setExampleList(list);
    return localExampleData;
  }

  public RootsData getRootsData(ExpReview paramExpReview)
  {
    RootsData localRootsData = new RootsData();
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramExpReview.roots.iterator();
    while (localIterator.hasNext())
    {
      Roots.InnerRoots localInnerRoots = (Roots.InnerRoots)localIterator.next();
      RootsContent localRootsContent = new RootsContent();
      localRootsContent.setContent(localInnerRoots.content).setMeaningCn(localInnerRoots.meaningCn);
      localArrayList.add(localRootsContent);
    }
    localRootsData.setRootsList(localArrayList);
    return localRootsData;
  }

  public VocabularyData getVocabularyData(ExpReview paramExpReview)
  {
    VocabularyData localVocabularyData = new VocabularyData();
    localVocabularyData.setVocabularyId(paramExpReview.id)
    	.setPron(paramExpReview.pron)
    	.setHasAudio(paramExpReview.hasAudio)
    	.setEnPos(paramExpReview.enDefinition.pos)
    	.setEnDefn(paramExpReview.enDefinition.defn)
    	.setContent(paramExpReview.content)
    	.setCnDefinition(paramExpReview.definition);
    return localVocabularyData;
  }
}