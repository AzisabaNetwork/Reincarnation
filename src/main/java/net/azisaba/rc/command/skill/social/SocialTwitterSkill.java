package net.azisaba.rc.command.skill.social;

public class SocialTwitterSkill extends AbstractSocialLinkSkill
{

    @Override
    public String getName()
    {
        return "social:twitter";
    }

    @Override
    public String getServiceName()
    {
        return "Twitter";
    }

    @Override
    public boolean isValidHandle(String handle)
    {
        return handle.length() <= 15;
    }

    @Override
    public void onLink(String handle)
    {
        this.user.setTwitter(handle);
    }
}
