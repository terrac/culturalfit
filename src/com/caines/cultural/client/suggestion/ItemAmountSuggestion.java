package com.caines.cultural.client.suggestion;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class ItemAmountSuggestion implements IsSerializable, Suggestion {

      private String s;
      private int amount;
      // Required for IsSerializable to work
      public ItemAmountSuggestion() {
      }

      // Convenience method for creation of a suggestion
      public ItemAmountSuggestion(String s,int amount) {
         this.s = s;
         this.amount = amount;
      }

      public String getDisplayString() {
          return s+":"+amount;
      }

      public String getReplacementString() {
          return s;
      }
   }