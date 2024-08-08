package net.azisaba.rc.command.skill.social;

public class SocialDiscordSkill extends AbstractSocialLinkSkill
{

    @Override
    public String getName()
    {
        return "social:discord";
    }

    @Override
    public String getServiceName()
    {
        return "Discord";
    }

    @Override
    public boolean isValidHandle(String handle)
    {
        return handle.length() <= 32;
    }

    @Override
    public void onLink(String handle)
    {
        this.user.setDiscord(handle);
    }
}
