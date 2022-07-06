package dinocraft.util;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.util.text.translation.I18n;

public class Lang
{
	private static final String REGEX = "\\|";
	private static final char CHAR = '|';
	private final String prefix;

	public Lang(String locKey)
	{
		this.prefix = locKey + ".";
	}

	public String getPrefix()
	{
		return this.prefix;
	}

	public String addPrefix(String suffix)
	{
		return this.prefix + suffix;
	}

	public String localize(String unloc, Object... args)
	{
		return this.localizeExact(this.addPrefix(unloc), args);
	}

	public String localize(String unloc)
	{
		return this.localizeExact(this.addPrefix(unloc));
	}

	public String localizeExact(String unloc, Object... args)
	{
		return I18n.translateToLocalFormatted(unloc, args);
	}

	public String localizeExact(String unloc)
	{
		return I18n.translateToLocal(unloc);
	}

	public String[] localizeList(String unloc, String... args)
	{
		return this.splitList(this.localize(unloc, (Object[]) args));
	}

	public String[] localizeList(String unloc)
	{
		return this.splitList(this.localize(unloc));
	}

	public List<String> localizeAll(List<String> unloc)
	{
		List<String> ret = new ArrayList<>();

		for (String s : unloc)
		{
			ret.add(this.localize(s));
		}

		return ret;
	}

	public String[] localizeAll(Lang lang, String... unloc)
	{
		String[] ret = new String[unloc.length];

		for (int i = 0; i < ret.length; i++)
		{
			ret[i] = lang.localize(unloc[i]);
		}

		return ret;
	}

	public String[] splitList(String list)
	{
		return list.split("\\|");
	}

	public String[] splitList(String list, String split)
	{
		return list.split(split);
	}

	public boolean canLocalize(String unloc)
	{
		return this.canLocalizeExact(this.addPrefix(unloc));
	}

	public boolean canLocalizeExact(String unloc)
	{
		return I18n.canTranslate(unloc);
	}
}