package com.textpicture.views.freetext.layer;

public interface SourceLoader<T> {

        T loadByName(String name);

}