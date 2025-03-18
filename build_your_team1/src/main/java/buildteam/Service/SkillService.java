package buildteam.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import buildteam.Model.Skill;
import buildteam.Model.Profile;
import buildteam.Dao.SkillDao;

import java.util.List;

@Service
public class SkillService {

    @Autowired
    private SkillDao skillDao;

    public void addSkill(String skillName, Profile profile) {
    	System.out.println("inside skillservice");
        Skill skill = new Skill();
        skill.setSkillName(skillName);
        skill.setProfile(profile);
        skillDao.save(skill);
    }

    public List<Skill> getSkillsByProfile(Profile profile) {
        return skillDao.findByProfile(profile);
    }
}

