package net.azisaba.rc.command.skill.social;

public class SocialYouTubeSkill extends AbstractSocialLinkSkill
{

    @Override
    public String getName()
    {
        return "social:youtube";
    }

    @Override
    public String getServiceName()
    {
        return "YouTube";
    }

    @Override
    public boolean isValidHandle(String handle)
    {
        return handle.length() <= 30;
    }

    @Override
    public void onLink(String handle)
    {
        this.user.setYoutube(handle);
    }
}
