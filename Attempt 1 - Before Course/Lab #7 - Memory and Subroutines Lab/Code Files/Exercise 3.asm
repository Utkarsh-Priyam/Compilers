.text 0x00400000
.globl main
main:
li $v0 5
syscall
move $a0 $v0
subu $sp, $sp, 4
sw $ra, ($sp)
jal fact
lw $ra, ($sp)
addu $sp, $sp, 4
move $a0 $v0
li $v0 1
syscall
li $v0 10
syscall

fact:
ble $a0 0 base
subu $a0 $a0 1
subu $sp, $sp, 4
sw $ra, ($sp)
jal fact
lw $ra, ($sp)
addu $sp, $sp, 4
addu $a0 $a0 1
mult $a0 $v0
mflo $v0
j end
base:
li $v0 1
end:
jr $ra