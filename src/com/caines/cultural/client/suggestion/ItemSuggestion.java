package com.caines.cultural.client.suggestion;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.google.gwt.user.client.ui.SuggestOracle.Suggestion;

public class ItemSuggestion implements IsSerializable, Suggestion {

      private String s;
      public boolean canEdit;
      // Required for IsSerializable to work
      public ItemSuggestion() {
      }

      // Convenience method for creation of a suggestion
      public ItemSuggestion(String s, boolean canEdit2) {
         this.s = s;
         this.canEdit = canEdit2;
      }

      public String getDisplayString() {
          return s;
      }

      public String getReplacementString() {
          return s;
      }
   }