package com.google.common.net;

import com.google.common.base.Ascii;
import com.google.common.base.CharMatcher;
import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.errorprone.annotations.Immutable;
import com.google.errorprone.annotations.concurrent.LazyInit;
import com.google.thirdparty.publicsuffix.PublicSuffixPatterns;
import com.google.thirdparty.publicsuffix.PublicSuffixType;
import java.util.List;
import javax.annotation.CheckForNull;

@Immutable
@ElementTypesAreNonnullByDefault
public final class InternetDomainName {
    private static final CharMatcher DASH_MATCHER = CharMatcher.anyOf("-_");
    private static final CharMatcher DIGIT_MATCHER = CharMatcher.inRange('0', '9');
    private static final CharMatcher DOTS_MATCHER = CharMatcher.anyOf(".。．｡");
    private static final Joiner DOT_JOINER = Joiner.on('.');
    private static final Splitter DOT_SPLITTER = Splitter.on('.');
    private static final CharMatcher LETTER_MATCHER = CharMatcher.inRange('a', 'z').or(CharMatcher.inRange('A', 'Z'));
    private static final int MAX_DOMAIN_PART_LENGTH = 63;
    private static final int MAX_LENGTH = 253;
    private static final int MAX_PARTS = 127;
    private static final int NO_SUFFIX_FOUND = -1;
    private static final CharMatcher PART_CHAR_MATCHER = DIGIT_MATCHER.or(LETTER_MATCHER).or(DASH_MATCHER);
    private static final int SUFFIX_NOT_INITIALIZED = -2;
    private final String name;
    private final ImmutableList<String> parts;
    @LazyInit
    private int publicSuffixIndexCache = -2;
    @LazyInit
    private int registrySuffixIndexCache = -2;

    InternetDomainName(String name2) {
        String name3 = Ascii.toLowerCase(DOTS_MATCHER.replaceFrom((CharSequence) name2, '.'));
        boolean z = true;
        name3 = name3.endsWith(".") ? name3.substring(0, name3.length() - 1) : name3;
        Preconditions.checkArgument(name3.length() <= MAX_LENGTH, "Domain name too long: '%s':", (Object) name3);
        this.name = name3;
        this.parts = ImmutableList.copyOf(DOT_SPLITTER.split(name3));
        Preconditions.checkArgument(this.parts.size() > 127 ? false : z, "Domain has too many parts: '%s'", (Object) name3);
        Preconditions.checkArgument(validateSyntax(this.parts), "Not a valid domain name: '%s'", (Object) name3);
    }

    private int publicSuffixIndex() {
        int publicSuffixIndexLocal = this.publicSuffixIndexCache;
        if (publicSuffixIndexLocal != -2) {
            return publicSuffixIndexLocal;
        }
        int findSuffixOfType = findSuffixOfType(Optional.absent());
        int publicSuffixIndexLocal2 = findSuffixOfType;
        this.publicSuffixIndexCache = findSuffixOfType;
        return publicSuffixIndexLocal2;
    }

    private int registrySuffixIndex() {
        int registrySuffixIndexLocal = this.registrySuffixIndexCache;
        if (registrySuffixIndexLocal != -2) {
            return registrySuffixIndexLocal;
        }
        int findSuffixOfType = findSuffixOfType(Optional.of(PublicSuffixType.REGISTRY));
        int registrySuffixIndexLocal2 = findSuffixOfType;
        this.registrySuffixIndexCache = findSuffixOfType;
        return registrySuffixIndexLocal2;
    }

    private int findSuffixOfType(Optional<PublicSuffixType> desiredType) {
        int partsSize = this.parts.size();
        for (int i = 0; i < partsSize; i++) {
            String ancestorName = DOT_JOINER.join((Iterable<? extends Object>) this.parts.subList(i, partsSize));
            if (i > 0 && matchesType(desiredType, Optional.fromNullable(PublicSuffixPatterns.UNDER.get(ancestorName)))) {
                return i - 1;
            }
            if (matchesType(desiredType, Optional.fromNullable(PublicSuffixPatterns.EXACT.get(ancestorName)))) {
                return i;
            }
            if (PublicSuffixPatterns.EXCLUDED.containsKey(ancestorName)) {
                return i + 1;
            }
        }
        return -1;
    }

    public static InternetDomainName from(String domain) {
        return new InternetDomainName((String) Preconditions.checkNotNull(domain));
    }

    private static boolean validateSyntax(List<String> parts2) {
        int lastIndex = parts2.size() - 1;
        if (!validatePart(parts2.get(lastIndex), true)) {
            return false;
        }
        for (int i = 0; i < lastIndex; i++) {
            if (!validatePart(parts2.get(i), false)) {
                return false;
            }
        }
        return true;
    }

    private static boolean validatePart(String part, boolean isFinalPart) {
        if (part.length() < 1 || part.length() > 63) {
            return false;
        }
        if (PART_CHAR_MATCHER.matchesAllOf(CharMatcher.ascii().retainFrom(part)) && !DASH_MATCHER.matches(part.charAt(0)) && !DASH_MATCHER.matches(part.charAt(part.length() - 1))) {
            return !isFinalPart || !DIGIT_MATCHER.matches(part.charAt(0));
        }
        return false;
    }

    public ImmutableList<String> parts() {
        return this.parts;
    }

    public boolean isPublicSuffix() {
        return publicSuffixIndex() == 0;
    }

    public boolean hasPublicSuffix() {
        return publicSuffixIndex() != -1;
    }

    @CheckForNull
    public InternetDomainName publicSuffix() {
        if (hasPublicSuffix()) {
            return ancestor(publicSuffixIndex());
        }
        return null;
    }

    public boolean isUnderPublicSuffix() {
        return publicSuffixIndex() > 0;
    }

    public boolean isTopPrivateDomain() {
        return publicSuffixIndex() == 1;
    }

    public InternetDomainName topPrivateDomain() {
        if (isTopPrivateDomain()) {
            return this;
        }
        Preconditions.checkState(isUnderPublicSuffix(), "Not under a public suffix: %s", (Object) this.name);
        return ancestor(publicSuffixIndex() - 1);
    }

    public boolean isRegistrySuffix() {
        return registrySuffixIndex() == 0;
    }

    public boolean hasRegistrySuffix() {
        return registrySuffixIndex() != -1;
    }

    @CheckForNull
    public InternetDomainName registrySuffix() {
        if (hasRegistrySuffix()) {
            return ancestor(registrySuffixIndex());
        }
        return null;
    }

    public boolean isUnderRegistrySuffix() {
        return registrySuffixIndex() > 0;
    }

    public boolean isTopDomainUnderRegistrySuffix() {
        return registrySuffixIndex() == 1;
    }

    public InternetDomainName topDomainUnderRegistrySuffix() {
        if (isTopDomainUnderRegistrySuffix()) {
            return this;
        }
        Preconditions.checkState(isUnderRegistrySuffix(), "Not under a registry suffix: %s", (Object) this.name);
        return ancestor(registrySuffixIndex() - 1);
    }

    public boolean hasParent() {
        return this.parts.size() > 1;
    }

    public InternetDomainName parent() {
        Preconditions.checkState(hasParent(), "Domain '%s' has no parent", (Object) this.name);
        return ancestor(1);
    }

    private InternetDomainName ancestor(int levels) {
        return from(DOT_JOINER.join((Iterable<? extends Object>) this.parts.subList(levels, this.parts.size())));
    }

    public InternetDomainName child(String leftParts) {
        return from(((String) Preconditions.checkNotNull(leftParts)) + "." + this.name);
    }

    public static boolean isValid(String name2) {
        try {
            InternetDomainName from = from(name2);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    private static boolean matchesType(Optional<PublicSuffixType> desiredType, Optional<PublicSuffixType> actualType) {
        return desiredType.isPresent() ? desiredType.equals(actualType) : actualType.isPresent();
    }

    public String toString() {
        return this.name;
    }

    public boolean equals(@CheckForNull Object object) {
        if (object == this) {
            return true;
        }
        if (object instanceof InternetDomainName) {
            return this.name.equals(((InternetDomainName) object).name);
        }
        return false;
    }

    public int hashCode() {
        return this.name.hashCode();
    }
}
