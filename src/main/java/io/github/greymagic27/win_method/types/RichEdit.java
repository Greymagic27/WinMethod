package io.github.greymagic27.win_method.types;

import io.github.greymagic27.win_method.IntSafe.DWORD;
import io.github.greymagic27.win_method.Structure;
import io.github.greymagic27.win_method.Union;
import io.github.greymagic27.win_method.WinDef.BYTE;
import io.github.greymagic27.win_method.WinDef.COLORREF;
import io.github.greymagic27.win_method.WinDef.UINT;
import io.github.greymagic27.win_method.WinDef.WORD;
import io.github.greymagic27.win_method.WinNT.LCID;
import io.github.greymagic27.win_method.WinNT.LONG;
import io.github.greymagic27.win_method.WinNT.SHORT;
import io.github.greymagic27.win_method.WinNT.WCHAR;

/// Values defined in richedit.h
public interface RichEdit {

    /// Sets character formatting in a rich edit control
    int EM_SETCHARFORMAT = WinUser.WM_USER + 68;

    /// Applies the formatting to the current selection. If the selection is empty, the character formatting is applied to the insertion point, and the new character format is in effect only until the insertion point changes
    int SCF_SELECTION = 0x0001;

    /// The {@link CHARFORMAT2W#crTextColor} member and the CFE_AUTOCOLOR value of the dwEffects member are valid
    int CFM_COLOR = 0x40000000;

    /// Contains information about character formatting in a rich edit control. CHARFORMAT2 is a Microsoft Rich Edit 2.0 extension of the {@link CHARFORMATW} structure, which allows you to use either structure with the EM_GETCHARFORMAT and {@link #EM_SETCHARFORMAT} messages
    @Structure.AutoFieldOrder
    class CHARFORMAT2W extends CHARFORMATW {
        /// Font weight. To use this member, set the CFM_WEIGHT flag in the dwMask member
        public WORD wWeight;
        /// Horizontal spacing between letters, in twips. This value has no effect on the text displayed by a rich edit control. To use this member, set the CFM_SPACING flag in the dwMask member
        public SHORT sSpacing;
        /// Background colour. To use this member, set the CFM_BACKCOLOR flag in the dwMask member
        public COLORREF crBackColor;
        /// A 32-bit locale identifier that contains a language identifier in the lower word and a sorting identifier and reserved value in the upper word. This has no effect on the text displayed. To use this member, set the CFM_LCID flag in the dwMask member
        public LCID lcid;
        /// Reserved, the value must be zero
        public DWORD dwReserved;
        /// Character style handle. This value has no effect on the text displayed by a rich edit control.  To use this member, set the CFM_STYLE flag in the dwMask member
        public SHORT sStyle;
        /// Value of the font size, above which to kern the character {@link #yHeight}. This value has no effect on the text displayed by a rich edit control. To use this member, set the CFM_KERNING flag in the dwMask member
        public WORD wKerning;
        /// Specifies the underline type. To use this member, set the CFM_UNDERLINETYPE flag in the dwMask member. This can be [one of the following values](https://learn.microsoft.com/en-us/windows/win32/api/richedit/ns-richedit-charformat2w)
        public BYTE bUnderlineType;
        /// Text animation type. This value has no effect on the text displayed by a rich edit control. To use this member, set the CFM_ANIMATION flag in the dwMask member
        public BYTE bAnimation;
        /// An index that identifies the author making a revision. The rich edit control uses different text colours for each different author index. To use this member, set the CFM_REVAUTHOR flag in the dwMask member
        public BYTE bRevAuthor;
        /// The underline colour. This can be one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/richedit/ns-richedit-charformat2w)
        public BYTE bUnderlineColor;

        /// Reserved, the value must be zero
        @AutoFieldOrder
        static class ReservedUnion extends Union {
            /// Reserved, the value must be zero
            public DWORD dwReserved;
            /// Client cookie
            public DWORD dwCookie;
        }
    }

    /// Contains information about character formatting in a rich edit control
    @Structure.AutoFieldOrder
    class CHARFORMATW extends Structure {
        /// Size in bytes of the specified structure. This member must be set before passing the structure to the rich edit control
        public UINT cbSize;
        /// Members containing valid information or attributes to set. This member can be zero, one, or more than one of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/richedit/ns-richedit-charformatw)
        public DWORD dwMask;
        /// Character effects. This member can be a combination of [the following values](https://learn.microsoft.com/en-us/windows/win32/api/richedit/ns-richedit-charformatw)
        public DWORD dwEffects;
        /// Character height, in twips (1/1440 of an inch or 1/20 of a printer's point)
        public LONG yHeight;
        /// Character offset, in twips, from the baseline. If the value of this member is positive, the character is a superscript; if it is negative, the character is a subscript
        public LONG yOffset;
        /// Text colour. This member is ignored if the CFE_AUTOCOLOR character effect is specified. To generate a {@link COLORREF}, use the RGB macro
        public COLORREF crTextColor;
        /// Character set value. The bCharSet member can be one of the values specified for the lfCharSet member of the LOGFONT structure
        public BYTE bCharSet;
        /// Font family and pitch. This member is the same as the lfPitchAndFamily member of the LOGFONT structure
        public BYTE bPitchAndFamily;
        /// Null-terminated character array specifying the font name
        @ArrayLength(32)
        public WCHAR[] szFaceName;
    }
}
