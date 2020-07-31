.text 0x00400000
.globl main
main:
li $v0 5
syscall
move $a0 $v0
li $v0 5
syscall
move $a1 $v0
subu $sp, $sp, 4
sw $ra, ($sp)
jal max2
lw $ra, ($sp)
addu $sp, $sp, 4
move $a0 $v0
li $v0 1
syscall
li $v0 10
syscall

max2:
blt $a0 $a1 second
move $v0 $a0
j end
second:
move $v0 $a1
end:
jr $ra