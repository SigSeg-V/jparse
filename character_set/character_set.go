package characterset

import (
	"strings"
)

const (
	nationalCharacters = "qwertyuiopasdfghjklzxcvbnm@#$"
)

func IsNationalCharacter(ch rune) bool {
	return strings.ContainsRune(nationalCharacters, ch)
}
