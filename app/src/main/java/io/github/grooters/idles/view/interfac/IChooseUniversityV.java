package io.github.grooters.idles.view.interfac;

import java.util.List;

public interface IChooseUniversityV extends IBaseV {
    void updateUniversity(List<String> universities);
    void changeUniversityInfo(String name);
}
