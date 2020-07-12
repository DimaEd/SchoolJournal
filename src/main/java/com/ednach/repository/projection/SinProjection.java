package com.ednach.repository.projection;

import com.ednach.model.Teacher;

/**
 * declare a projection interface for the sin class
 */
public interface SinProjection {

    Long getSinId();

    String getTypeSin();

    Long getPoints();

    Long getSchoolboyId();

    Long getUserId();

    String getUserFirstName();

    String getUserLastName();

    Long getClassroomId();

    Teacher getTeacher();

    String getClassName();

    Long getTeacherId();
}
